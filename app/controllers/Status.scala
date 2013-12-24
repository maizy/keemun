package controllers

import play.api.mvc.{Action, Controller}
import hedgehog.controllers.WithViewContext

/**
 * Copyright (c) Nikita Kovaliov, maizy.ru, 2013
 * See LICENSE.txt for details.
 */
object Status extends Controller with WithViewContext {
  def index = Action { implicit request =>
    Ok(views.html.status())
  }
}
