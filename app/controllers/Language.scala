package controllers

import play.api.mvc.{Action, Controller}
import play.api.i18n.Lang
import play.api.Play.current
import scala.util.control.NonFatal


/**
 * Copyright (c) Nikita Kovaliov, maizy.ru, 2013
 * See LICENSE.txt for details.
 */
object Language extends Controller {

  def set = Action(parse.tolerantFormUrlEncoded) { implicit request =>
    //FIXME: simplify with scala shugar
    var lang: Option[Lang] = None
    val maybeLang = request.body.get("lang").map(_.last)
    if (maybeLang.isDefined) {
      try {
        lang = Some(Lang(maybeLang.get))
      } catch {
        case NonFatal(e) =>
      }
    }
    if (maybeLang.isEmpty) {
      BadRequest("Language is required")
    } else if (lang.isEmpty) {
      BadRequest("Unknown language \"" + maybeLang.getOrElse("") + "\"")
    } else {
      Redirect(request.headers.get(REFERER).getOrElse("/")).withLang(lang.get)
    }
  }
}
