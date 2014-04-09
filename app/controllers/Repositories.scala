package controllers

import play.api.mvc.{Action, Controller}
import play.api.libs.json.Json
import hedgehog.models.{Repo, ConfigError}

/**
 * Copyright (c) Nikita Kovaliov, maizy.ru, 2013
 * See LICENSE.txt for details.
 */
object Repositories extends Controller {
  def list = Action { implicit request =>
    try {
      Ok(Json.obj(
        "repositories" -> Repo.getCurrentRepos
      ))
    } catch {
      case ex: ConfigError => InternalServerError(ex.toString)
    }
  }
}
