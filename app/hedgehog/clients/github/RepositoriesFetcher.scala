package hedgehog.clients.github

import scala.concurrent.{Future, Promise}
import scala.concurrent.duration._
import scala.collection.concurrent.TrieMap
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

  private val fetching = new FetchingRepos()
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
    
      if (!fetching.isFetchNeeded(accountSettings, promise)) {
        Logger.debug(s"Fetching ever run for $accountSettings")
      } else {
        val key = getSettingsKey(accountSettings)
        Logger.debug(s"Fetch repos for $accountSettings")

        def sendResult(res: Repos) {
          fetching.finishPromises(accountSettings, p => p.success(res))
        }

        client.getRepos(accountSettings) map {
          case res =>
            Cache.set(key, res, CACHE_TIME)
            res
        } recoverWith {
          case _ =>
            Cache.getAs[Repos](key) match {
              case Some(repos) =>
                Logger.warn(s"Unable to fetch repos for $accountSettings get old version from cache")
                Future.successful(repos)
              case None =>
                Logger.error(s"Errors when fetch repos for $accountSettings, skipping")
                //return empty seq on errors
                Future.successful(Seq[Repo]())
            }
        } map {
          case res => sendResult(res)
        }
      }
      promise.future
  }

  def reload(accountSettings: Seq[AccountSettings]): Future[Boolean] = {
    Future.sequence(accountSettings.map(fetch)) map {
      case _ => true
    }
  }

  def hits = hits_
  def misses = misses_
}


object RepositoriesFetcher {
  lazy val playAppInstance: RepositoriesFetcher = new RepositoriesFetcher(hedgehog.Config.playAppInstance.githubClient)
}


private class FetchingRepos() {
  type ReposPromise = Promise[Seq[Repo]]

  val nowFetched = TrieMap[AccountSettings, Seq[ReposPromise]]()

  def isFetchNeeded(accountSettings: AccountSettings, promise: ReposPromise): Boolean = {
    this.synchronized {
      val promises = this.popCurrentPromises(accountSettings)
      val needFetch = promises.length == 0
      nowFetched(accountSettings) = promise +: promises
      needFetch
    }
  }
  
  def finishPromises(accountSettings: AccountSettings, func: ReposPromise => Unit) {
    popCurrentPromises(accountSettings).foreach(func)
  }

  private def popCurrentPromises(accountSettings: AccountSettings): Seq[ReposPromise] = {
    this.synchronized {
      val promises = nowFetched.getOrElse(accountSettings, Seq[ReposPromise]())
      nowFetched.remove(accountSettings)
      promises
    }
  }
}
