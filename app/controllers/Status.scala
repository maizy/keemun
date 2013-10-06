package controllers

import play.api._
import play.api.mvc._

object Status extends Controller {
  def index = Action {
    Ok(views.html.status())
  }
}
