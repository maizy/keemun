package controllers

import play.api.mvc.{Action, Controller}
import play.api.Play.current
import hedgehog.controllers.WithViewContext
import hedgehog.clients.github

/**
 * Copyright (c) Nikita Kovaliov, maizy.ru, 2013
 * See LICENSE.txt for details.
 */
object Status extends Controller with WithViewContext {
  def index = Action { implicit request =>
    Ok(views.html.status(github.Config.fromPlayApp(current)))
  }
}
