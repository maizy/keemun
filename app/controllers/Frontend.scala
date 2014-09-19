package controllers

import play.api.mvc.{Action, Controller}
import hedgehog.controllers.WithViewContext


object Frontend extends Controller with WithViewContext {

  def codeSearch = Action { implicit request =>
    Ok(views.html.search())
  }

  def redirectToCodeSearch = Action {
    Redirect("/search/code")
  }
  

  def about = Action { implicit request =>
    Ok(views.html.about())
  }

}
