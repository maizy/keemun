package controllers

import play.api.mvc.{Action, Controller}
import play.api.libs.json.{Json, JsNull}

import keemun.controllers.{JsonBackend, fetchRepositories}

/**
 * Copyright (c) Nikita Kovaliov, maizy.ru, 2013-2014
 * See LICENSE.txt for details.
 */
object Repositories extends Controller with JsonBackend {
  def list = Action.async { implicit request =>
    import play.api.libs.concurrent.Execution.Implicits._
    fetchRepositories() map {
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
