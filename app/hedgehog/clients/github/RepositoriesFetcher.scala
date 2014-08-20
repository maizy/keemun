package hedgehog.clients.github

import scala.concurrent.{Future, Promise}
import scala.concurrent.duration._
import scala.collection.mutable
import play.api.Logger
import play.api.cache.Cache
import hedgehog.models.{AccountSettings, Repo}


/**
 * Copyright (c) Nikita Kovaliov, maizy.ru, 2014
 * See LICENSE.txt for details.
 */
class RepositoriesFetcher(client: Client) {
  type Repos = Seq[Repo]

  private var hits_ = 0
  private var misses_ = 0

  private val CACHE_TIME = 5.minutes //TODO: from configs

  private val fetching = mutable.Map[AccountSettings, mutable.ListBuffer[Promise[Repos]]]() //FIXME: thread unsafe
  private implicit val context = client.context
  private implicit val app = play.api.Play.current  //TODO: get from client

  private def getSettingsKey(settings: AccountSettings): String = s"repos-${settings.hashCode().toHexString}"

  def getRepos(accountsSettings: Seq[AccountSettings]): Future[Repos] = {
    val requests = Future.sequence(
      accountsSettings.map {
        accountSettings =>
          val key = getSettingsKey(accountSettings)
          Cache.getAs[Repos](key) match {
            case Some(repos) =>
              hits_ += 1
              Logger.debug(s"Get repos for $accountSettings from cache")
              Future.successful(repos)
            case None =>
              misses_ += 1
              fetch(accountSettings)
          }
      }
    )
    requests.map(_.flatten)
  }
  
  private def fetch(accountSettings: AccountSettings): Future[Repos] = {
      val promise = Promise[Repos]()
      // other fetch runs in parallel
      if (fetching contains accountSettings) {
        Logger.debug(s"Fetching ever run for $accountSettings, prepend promise")
        fetching(accountSettings) prepend promise

      // first fetch
      } else {
        val key = getSettingsKey(accountSettings)
        Logger.debug(s"Fetch repos for $accountSettings")
        fetching(accountSettings) = mutable.ListBuffer(promise)
        val clientResult = client.getRepos(accountSettings)
        def sendResult(res: Repos) {
          fetching(accountSettings).foreach(_.success(res))
          fetching -= accountSettings
        }
        clientResult onFailure {
          case e =>
            Cache.getAs[Repos](key) match {
              case Some(repos) =>
                Logger.warn(s"Unable to fetch repos for $accountSettings get old version from cache")
                sendResult(repos)
              case None =>
                Logger.error(s"Errors when fetch repos for $accountSettings, skipping")
                //return empty seq on errors
                sendResult(Seq[Repo]())
            }
        }

        clientResult onSuccess {
          case res =>
            Cache.set(key, res, CACHE_TIME)
            sendResult(res)
        }
      }
      promise.future
  }

  def reload(accountSettings: Seq[AccountSettings]): Future[Seq[(AccountSettings, Boolean)]] = {
    //val p = Promise[Seq[(AccountSettings, Boolean)]]
    Future {
      List()
    }
  }

  def hits = hits_
  def misses = misses_
}


object RepositoriesFetcher {
  lazy val playAppInstance: RepositoriesFetcher = new RepositoriesFetcher(hedgehog.Config.playAppInstance.githubClient)
}
