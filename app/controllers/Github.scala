package controllers

import play.api.mvc.{Action, Controller}
import play.api.libs.json.Json
import play.api.libs.concurrent.Execution.Implicits._
import hedgehog.models.{GithubOrg, Repo, GithubUser}
import hedgehog.clients.github.RepositoriesFetcher


/**
 * Copyright (c) Nikita Kovaliov, maizy.ru, 2014
 * See LICENSE.txt for details.
 */
object Github extends Controller {

  def forceUpdate = Action {
//    val config = hedgehog.Config.playAppInstance
//    RepositoriesFetcher.playAppInstance.reload(config.sources.accountsSettings).map {
//      Ok(Json.obj("success" -> false))
//    }
    Ok("1")
  }

}
