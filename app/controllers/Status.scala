package controllers

import play.api.mvc.{Action, Controller}
import hedgehog.controllers.WithViewContext
import hedgehog.Config.playAppInstance

/**
 * Copyright (c) Nikita Kovaliov, maizy.ru, 2013-2014
 * See LICENSE.txt for details.
 */
object Status extends Controller with WithViewContext {
  def index = Action { implicit request =>
    Ok(views.html.status(playAppInstance))
  }
}
