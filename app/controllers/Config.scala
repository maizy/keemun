package controllers

import play.api.mvc.{Action, Controller}
import play.api.libs.json.Json


/**
 * Copyright (c) Nikita Kovaliov, maizy.ru, 2013-2014
 * See LICENSE.txt for details.
 */
object Config extends Controller {

  def list = Action { implicit request =>
    import keemun.models.AccountSettings.accountSettingWrites
    Ok(Json.obj(
        "sources" -> keemun.Config.playAppInstance.sources.accountsSettings
      ))
  }

}
