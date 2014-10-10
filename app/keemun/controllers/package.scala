package keemun

import scala.concurrent.Future

import keemun.clients.github.RepositoriesFetcher
import keemun.models.Repo


/**
 * Copyright (c) Nikita Kovaliov, maizy.ru, 2014
 * See LICENSE.txt for details.
 */
package object controllers {
  def fetchRepositories(): Future[Seq[Repo]] = {
    val fetcher = RepositoriesFetcher.playAppInstance
    val config = keemun.Config.playAppInstance
    fetcher.getRepos(config.sources.accountsSettings)
  }
}
