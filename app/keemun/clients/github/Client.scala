package keemun.clients.github

import scala.concurrent.Future

import keemun.models.{AccountSettings, Repo}
import keemun.clients.github.resources.Repositories

/**
 * Copyright (c) Nikita Kovaliov, maizy.ru, 2014-2015
 * See LICENSE.txt for details.
 */
class Client(val config: Config, implicit val context: scala.concurrent.ExecutionContext) {

  def getReposByAccount(accountSettings: AccountSettings): Future[Seq[Repo]] =
    Repositories.getAllByAccount(config, accountSettings)
  
  def getReposForAccounts(accountsSettings: Seq[AccountSettings]): Future[Seq[Repo]] =
    Future.sequence(accountsSettings map getReposByAccount).map(_.flatten)

}
