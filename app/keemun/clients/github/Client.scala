package keemun.clients.github

//import keemun.clients.github.resource.ResourceAsyncCache

import scala.concurrent.{Future, ExecutionContext}

import keemun.models.{AccountSettings, Repo}
import keemun.clients.github.resources.Repositories

/**
 * Copyright (c) Nikita Kovaliov, maizy.ru, 2014-2015
 * See LICENSE.txt for details.
 */
class Client(val config: Config, implicit val ec: ExecutionContext) {

  lazy val repositoriesResource = new Repositories(config)

  def getReposByAccount(accountSettings: AccountSettings): Future[Seq[Repo]] =
    repositoriesResource.get(accountSettings)

  def getReposForAccounts(accountsSettings: Seq[AccountSettings]): Future[Seq[Repo]] =
    Future.sequence(accountsSettings map getReposByAccount).map(_.flatten)

}
