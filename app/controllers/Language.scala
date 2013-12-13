package controllers

import play.api.mvc.{Action, Controller}
import play.api.i18n.Lang
import play.api.Play.current


/**
 * Copyright (c) Nikita Kovaliov, maizy.ru, 2013
 * See LICENSE.txt for details.
 */
object Language extends Controller {

  def set = Action(parse.tolerantFormUrlEncoded) { implicit request =>
    val avalableLangCodes = Lang.availables.map(_.code)
    val maybeLang = request.body.get("lang")
      .map(_.last)
      .filter(avalableLangCodes.contains(_))
      .map(Lang(_))
    if (maybeLang.isEmpty) {
      BadRequest("One of those \""+ avalableLangCodes.mkString("\", \"") +"\" languages is required")
    } else {
      Redirect(request.headers.get(REFERER).getOrElse("/")).withLang(maybeLang.get)
    }
  }
}
