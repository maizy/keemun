package controllers

import play.api.mvc.{Action, Controller}
import play.api.libs.json.Json
import hedgehog.clients.github.RepositoriesFetcher
import hedgehog.controllers.JsonBackend


/**
 * Copyright (c) Nikita Kovaliov, maizy.ru, 2014
 * See LICENSE.txt for details.
 */
object Github extends Controller with JsonBackend {

  def reload = Action.async {
    val fetcher = RepositoriesFetcher.playAppInstance
    val config = hedgehog.Config.playAppInstance
    import play.api.libs.concurrent.Execution.Implicits._

    fetcher.reload(config.sources.accountsSettings) map {
      success =>
        Ok(Json.obj(
          "success" -> success
        ))
    } recover commonJsonRecover
  }

}
