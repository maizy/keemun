package controllers

import play.api.Routes
import play.api.mvc.{Controller, Action}

/**
 * Copyright (c) Nikita Kovaliov, maizy.ru, 2013
 * See LICENSE.txt for details.
 */
object JsHelpers extends Controller {

  def jsRoutes = Action { implicit request =>
    import routes.javascript._
    Ok(
      Routes.javascriptRouter("_routes")(
        Repositories.list,
        Frontend.codeSearch,
        Frontend.about
      )
    ).as("text/javascript")
  }

}
