package controllers

import play.api.mvc.{Action, Controller}
import play.api.libs.json.Json

import hedgehog.models.Sources



/**
 * Copyright (c) Nikita Kovaliov, maizy.ru, 2013
 * See LICENSE.txt for details.
 */
object Config extends Controller {

  def list = Action { implicit request =>
    import hedgehog.models.AccountSettings.accountSettingWrites
    Ok(Json.obj(
        "sources" -> Sources.accountsSettings
      ))
  }

}
