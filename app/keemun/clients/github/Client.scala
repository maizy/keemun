package keemun.clients.github

import scala.concurrent.{Future, Promise}
import play.api.libs.ws.WS
import play.api.libs.json.{JsSuccess, JsError}
import play.api.Logger

import keemun.models.{AccountSettings, Repo}

/**
 * Copyright (c) Nikita Kovaliov, maizy.ru, 2014
 * See LICENSE.txt for details.
 */
class Client(val config: Config, implicit val context: scala.concurrent.ExecutionContext) {

  val PER_PAGE = 100

  def getRepos(accountSettings: AccountSettings): Future[Seq[Repo]] = {
    val resultPromise = Promise[Seq[Repo]]()

    val wrapError: PartialFunction[Throwable, Unit] = {
      //TODO: custom error subclasses
      case err => resultPromise.failure(new FetchError(cause = err))
    }
    val getFirstPage = getPage(accountSettings, page = 1, perPage = PER_PAGE)

    getFirstPage onSuccess {
      case PageRes(res, None, None) => resultPromise.success(res)
      case PageRes(firstPageRes, _, Some(last)) =>
        require(last > 1)
        val otherPagesRes = Future.sequence(
          for {
            page <- 2 to last
          } yield getPage(accountSettings, page, perPage = PER_PAGE)
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
    val url = config.replaceBaseUrl(account.apiReposUrl)
    val httpClient = (
        config.httpClient.url(url).withQueryString(
          "per_page" -> perPage.toString,
          "page" -> page.toString,
          "type" -> "open"
        )
        match {
          case h if accountSettings.includePrivateRepos => h.withQueryString("type" ->"private")
          case h => h
        }
      ) match {
        case h if config.accessToken.isDefined => h.withHeaders("Authorization" -> s"token ${config.accessToken.get}")
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


private case class PageRes(repos: Seq[Repo], nextPage: Option[Int], lastPage: Option[Int]) {
  val currentPage: Option[Int] = nextPage map (_ - 1)
}
