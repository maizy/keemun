package controllers

import play.api.mvc.{Action, Controller}
import play.api.libs.json.Json
import hedgehog.models.Repo

/**
 * Copyright (c) Nikita Kovaliov, maizy.ru, 2013
 * See LICENSE.txt for details.
 */
object Repositories extends Controller {
  def list = Action { implicit request =>
    val repos = Repo.getCurrentRepos
    Ok(Json.obj(
      "repositories" -> repos
      //"groups" -> Json.
    ))
  }
}
