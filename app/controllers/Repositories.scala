package controllers

import play.api.mvc.{Action, Controller}
import play.api.libs.json.Json

import hedgehog.clients.github.RepositoriesFetcher
import hedgehog.controllers.JsonBackend

/**
 * Copyright (c) Nikita Kovaliov, maizy.ru, 2013-2014
 * See LICENSE.txt for details.
 */
object Repositories extends Controller with JsonBackend {
  def list = Action.async { implicit request =>
    val fetcher = RepositoriesFetcher.playAppInstance
    val config = hedgehog.Config.playAppInstance
    import play.api.libs.concurrent.Execution.Implicits._

    fetcher.getRepos(config.sources.accountsSettings) map {
      repos =>
        Ok(Json.obj(
          //TODO: grouping, params ...
          "repositories" -> repos
        ))
    } recover commonJsonRecover
  }
}
