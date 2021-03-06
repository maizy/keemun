package keemun.views

import play.api.mvc.{AnyContent, Request}
import play.api.i18n
import play.api.Play.current
import play.api.libs.json.Json

import keemun.Config.playAppInstance

/**
 * Copyright (c) Nikita Kovaliov, maizy.ru, 2013
 * See LICENSE.txt for details.
 */
class ViewContext(
    val controller: Request[AnyContent],
    val requestLang: i18n.Lang) {
  lazy val availableLanguages = i18n.Lang.availables
  val lang: i18n.Lang = requestLang
  val projectRepoUrl = playAppInstance.projectRepoUrl
  val version = playAppInstance.version
  val copyrightYears = playAppInstance.copyrightYears
  val jsState = Json.obj(
    "lang" -> requestLang.code,
    "country" -> requestLang.country,
    "available_langs" -> availableLanguages.map(_.code)
  )

  def Messages(key: String, args: Any*) = {
    i18n.Messages(key, args)(lang)
  }
}
