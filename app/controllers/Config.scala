package controllers

import play.api.mvc.{Action, Controller}
import play.api.libs.json.Json


/**
 * Copyright (c) Nikita Kovaliov, maizy.ru, 2013-2014
 * See LICENSE.txt for details.
 */
object Config extends Controller {

  def list = Action { implicit request =>
    import hedgehog.models.AccountSettings.accountSettingWrites
    Ok(Json.obj(
        "sources" -> hedgehog.Config.playAppInstance.sources.accountsSettings
      ))
  }

}
