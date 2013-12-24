package hedgehog.views

import play.api.mvc.{AnyContent, Request}
import play.api.i18n
import play.api.Play.current

/**
 * Copyright (c) Nikita Kovaliov, maizy.ru, 2013
 * See LICENSE.txt for details.
 */
class ViewContext (val controller: Request[AnyContent], val requestLang: i18n.Lang) {
  lazy val availableLanguages = i18n.Lang.availables
  val lang: i18n.Lang = requestLang
  val hedgehogRepoUrl = "https://github.com/maizy/hedGeHog"
  val version = "0.0.1" //TODO: from config
  val copyrightYears = "2013"

  def Messages(key: String, args: Any*) = {
    i18n.Messages(key, args)(lang)
  }
}
