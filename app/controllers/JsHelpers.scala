package controllers

import play.api.Routes
import play.api.mvc.{Controller, Action}

import jsmessages.api.JsMessages

/**
 * Copyright (c) Nikita Kovaliov, maizy.ru, 2013
 * See LICENSE.txt for details.
 */
object JsHelpers extends Controller {

  /**
   * routes for js
   */
  def jsRoutes = Action { implicit request =>
    import routes.javascript._
    Ok(
      Routes.javascriptRouter("_routes")(
        Repositories.list,
        Frontend.codeSearch,
        Frontend.about,
        Statistics.statistics
      )
    ).as("text/javascript")
  }

  /**
   * translations for js
   */
  def jsMessages = Action { implicit request =>
    Ok(JsMessages.default(play.api.Play.current)(Some("window._messages")))
  }

}
