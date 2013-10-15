package controllers

import play.api._
import play.api.mvc._

object Status extends Controller {
  def index = Action { implicit request =>
    Ok(views.html.status())
  }
}
