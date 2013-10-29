package controllers

import play.api._
import play.api.mvc._


object Frontend extends Controller {

  def search = Action { implicit request =>
    Ok(views.html.search())
  }

  def about = Action { implicit request =>
    Ok(views.html.about(version = "0.0.1"))  //TODO: use project config
  }

}
