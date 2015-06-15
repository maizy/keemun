package controllers

import play.api.mvc.{Action, Controller}
import play.api.libs.json.Json
import keemun.controllers.JsonBackend

import scala.concurrent.Future


/**
 * Copyright (c) Nikita Kovaliov, maizy.ru, 2014
 * See LICENSE.txt for details.
 */
object Github extends Controller with JsonBackend {

  def reload = Action.async {
    //FIXME todo
    /*val fetcher = RepositoriesFetcher.playAppInstance
    val config = keemun.Config.playAppInstance
    import play.api.libs.concurrent.Execution.Implicits._

    fetcher.reload(config.sources.accountsSettings) map {
      success =>
        Ok(Json.obj(
          "success" -> success
        ))
    } recover commonJsonRecover
    */
    Future.successful(
      Ok(Json.obj(
          "success" -> true
        ))
    )
  }

}
