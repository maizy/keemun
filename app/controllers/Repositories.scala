package controllers

import play.api._
import play.api.mvc._
import play.api.libs.json._
import hedgehog.models._

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
