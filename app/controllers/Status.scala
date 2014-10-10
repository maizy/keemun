package controllers

import play.api.mvc.{Action, Controller}
import play.core.PlayVersion
import keemun.controllers.WithViewContext
import keemun.Config.playAppInstance
import keemun.clients.github.RepositoriesFetcher

/**
 * Copyright (c) Nikita Kovaliov, maizy.ru, 2013-2014
 * See LICENSE.txt for details.
 */
object Status extends Controller with WithViewContext {
  def index = Action { implicit request =>
    val fetcherStat = Map(
      "hits" -> RepositoriesFetcher.playAppInstance.hits,
      "misses" -> RepositoriesFetcher.playAppInstance.misses
    )
    Ok(views.html.status(
      playAppInstance,
      PlayVersion.current,
      PlayVersion.scalaVersion,
      fetcherStat,
      Some(List(
        Option(System.getProperty("java.vm.vendor")),
        Option(System.getProperty("java.vm.name")),
        Option(System.getProperty("java.version"))
      ).flatten.mkString(" ")).filterNot {_.length == 0}
    ))
  }
}
