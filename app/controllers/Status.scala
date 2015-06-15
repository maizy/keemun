package controllers

import play.api.mvc.{Action, Controller}
import play.core.PlayVersion
import keemun.controllers.WithViewContext
import keemun.Config.playAppInstance

/**
 * Copyright (c) Nikita Kovaliov, maizy.ru, 2013-2014
 * See LICENSE.txt for details.
 */
object Status extends Controller with WithViewContext {
  def index = Action { implicit request =>
    Ok(views.html.status(
      playAppInstance,
      PlayVersion.current,
      PlayVersion.scalaVersion,
      Some(List(
        Option(System.getProperty("java.vm.vendor")),
        Option(System.getProperty("java.vm.name")),
        Option(System.getProperty("java.version"))
      ).flatten.mkString(" ")).filterNot {_.length == 0}
    ))
  }
}
