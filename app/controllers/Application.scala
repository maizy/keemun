package controllers

import play.api._
import play.api.mvc._


object Application extends Controller {

  def search = Action { implicit request =>
    Ok(views.html.search())
  }

  def about = Action {
    Ok(views.html.about(version = "0.0.1"))  //TODO: use project config
  }
  
}
