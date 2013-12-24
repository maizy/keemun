package controllers

import play.api.mvc.{Action, Controller}
import play.api.i18n.Lang
import play.api.Play.current
import hedgehog.controllers.PostArguments


/**
 * Copyright (c) Nikita Kovaliov, maizy.ru, 2013
 * See LICENSE.txt for details.
 */
object Language extends Controller with PostArguments {

  def set = Action { implicit request =>
    val avalableLangsCodes = Lang.availables.map(_.code)
    postArg("lang")
      .filter(avalableLangsCodes.contains(_))
      .map(Lang(_))
      .map(l => Redirect(request.headers.get(REFERER).getOrElse("/")).withLang(l))
      .getOrElse(BadRequest("One of those \""+ avalableLangsCodes.mkString("\", \"") +"\" languages is required"))
  }
}
