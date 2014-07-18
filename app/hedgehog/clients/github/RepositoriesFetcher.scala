package hedgehog.clients.github

import scala.concurrent.{Future, Promise}
import scala.collection.mutable
import com.github.nscala_time.time.Imports.DateTime
import hedgehog.models.{AccountSettings, Repo}


/**
 * Copyright (c) Nikita Kovaliov, maizy.ru, 2014
 * See LICENSE.txt for details.
 */
class RepositoriesFetcher(client: Client) {
  val repositories: mutable.Map[String, Option[Seq[Repo]]] = mutable.Map.empty
  val lastFetched: mutable.Map[String, Option[DateTime]] = mutable.Map.empty

  implicit val context = client.context

  def getRepos(accountsSettings: Seq[AccountSettings]): Future[Seq[Repo]] = {
    //TODO: cache
    fetch(accountsSettings)
  }
  
  private def fetch(accountsSettings: Seq[AccountSettings]): Future[Seq[Repo]] = {
    val requests = Future.sequence{
      accountsSettings.map(client.getRepos)
    }
    val future = requests map {
      case r => r.flatten
    }
    future
  }

  def reload(accountSettings: Seq[AccountSettings]): Future[Seq[(AccountSettings, Boolean)]] = {
    //val p = Promise[Seq[(AccountSettings, Boolean)]]
    Future {
      List()
    }
  }
}


object RepositoriesFetcher {
  lazy val playAppInstance: RepositoriesFetcher = new RepositoriesFetcher(hedgehog.Config.playAppInstance.githubClient)
}
