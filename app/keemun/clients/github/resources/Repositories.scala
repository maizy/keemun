package keemun.clients.github.resources

import scala.concurrent.duration.{Duration, DurationInt}
import scala.concurrent.{ExecutionContext, Future, Promise}
import scala.reflect.ClassTag

import play.api.libs.json.{JsSuccess, JsError}
import play.api.Logger

import keemun.clients.github.resource.{RequestKey, ResourceAsyncCache, Resource}
import keemun.models.{AccountSettings, Repo}
import keemun.clients.github.{FetchError, Config, GithubReades, parseLinkHeader}

/**
 * Copyright (c) Nikita Kovaliov, maizy.ru, 2015
 * See LICENSE.txt for details.
 */
class RepositoriesBase(clientConfig: Config)(implicit protected override val context: ExecutionContext)
    extends Resource[AccountSettings, Seq[Repo]](clientConfig) {

  val PER_PAGE = 100

  private case class PageRes(repos: Seq[Repo], nextPage: Option[Int], lastPage: Option[Int]) {
    val currentPage: Option[Int] = nextPage map (_ - 1)
  }

  def get(params: AccountSettings): Future[Seq[Repo]] = params match {
    case (accountSettings: AccountSettings) =>
      val resultPromise = Promise[Seq[Repo]]()

      val wrapError: PartialFunction[Throwable, Unit] = {
        //TODO: custom error subclasses
        case err => resultPromise.failure(new FetchError(cause = err))
      }
      val getFirstPage = getPage(accountSettings, 1, PER_PAGE)

      getFirstPage onSuccess {
        case PageRes(res, None, None) => resultPromise.success(res)
        case PageRes(firstPageRes, _, Some(last)) =>
          require(last > 1)
          val otherPagesRes = Future.sequence(
            for {
              page <- 2 to last
            } yield getPage(accountSettings, page, PER_PAGE)
          )
          otherPagesRes onSuccess {
            case otherPages => resultPromise success (firstPageRes ++ otherPages.flatMap(_.repos))
          }
          otherPagesRes onFailure wrapError
      }
      getFirstPage onFailure wrapError

      resultPromise.future
  }

  private def getPage(accountSettings: AccountSettings, page: Int, perPage: Int): Future[PageRes] = {
    Logger.debug(s"Fetch repo page: page=$page, perPage=$perPage, account=${accountSettings.account.name}")
    val account = accountSettings.account
    val url = clientConfig.replaceBaseUrl(account.apiReposUrl)
    val httpClient = (
        clientConfig.httpClient.url(url).withQueryString(
          "per_page" -> perPage.toString,
          "page" -> page.toString,
          "type" -> "open"
        )
        match {
          case h if accountSettings.includePrivateRepos => h.withQueryString("type" ->"private")
          case h => h
        }
      ) match {
        case h if clientConfig.accessToken.isDefined =>
          h.withHeaders("Authorization" -> s"token ${clientConfig.accessToken.get}")
        case h => h
      }

    httpClient.get map {
      case response =>
        response.status match {
          case 200 =>
            GithubReades.repoSeqReads.reads(response.json) match {
              case JsSuccess(res, _) =>
                val rels = response.header("Link").map(parseLinkHeader)
                PageRes(
                  res,
                  nextPage = rels.flatMap(_.get("next").flatMap(_.page)),
                  lastPage = rels.flatMap(_.get("last")).flatMap(_.page))
              case JsError(err) => throw new FetchError(s"Unable to parse repo json $err")
            }
          //TODO: custom exceptions subclasses
          case code: Int if code >= 400 && code < 599 => throw new FetchError("No data for repos request")
          case _ => throw new FetchError("Unknown error when fetching repos")
        }
    }
  }
}


class Repositories(clientConfig: Config)
        (implicit protected override val context: ExecutionContext,
         implicit protected override val classTag: ClassTag[Seq[Repo]])
    extends RepositoriesBase(clientConfig)
    with RequestKey[AccountSettings, Seq[Repo]]
    with ResourceAsyncCache[AccountSettings, Seq[Repo]] {

  override val ttl: Duration = 10.minutes

  /**
   * function to compute String key from input params
   */
  override def computeKey(params: AccountSettings): String = s"repos-${params.hashCode().toHexString}"
}
