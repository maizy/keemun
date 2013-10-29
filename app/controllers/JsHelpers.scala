package controllers

import play.api._
import play.api.mvc._

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
        Frontend.search,
        Frontend.about
      )
    ).as("text/javascript")
  }

}
