package controllers

import play.api.mvc.{Action, Controller}
import hedgehog.controllers.WithViewContext


object Frontend extends Controller with WithViewContext {

  def search = Action { implicit request =>
    Ok(views.html.search())
  }

  def about = Action { implicit request =>
    Ok(views.html.about(version = "0.0.1"))  //TODO: use project config
  }

}
