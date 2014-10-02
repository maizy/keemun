package controllers

import play.api.mvc.{Action, Controller}
import play.api.libs.json.{Json, JsNull}

import keemun.clients.github.RepositoriesFetcher
import keemun.controllers.JsonBackend

/**
 * Copyright (c) Nikita Kovaliov, maizy.ru, 2013-2014
 * See LICENSE.txt for details.
 */
object Repositories extends Controller with JsonBackend {
  def list = Action.async { implicit request =>
    val fetcher = RepositoriesFetcher.playAppInstance
    val config = keemun.Config.playAppInstance
    import play.api.libs.concurrent.Execution.Implicits._

    fetcher.getRepos(config.sources.accountsSettings) map {
      repos =>
        Ok(Json.obj(
          "repositories" -> repos,

          //TODO: (iss #26) grouping, params ...
          "params" -> Json.obj(
            "sort" -> JsNull,
            "direction" -> "asc",
            "group" -> JsNull
          )
        ))
    } recover commonJsonRecover
  }
}
