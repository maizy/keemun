package controllers

import play.api.mvc.{Action, Controller}
import play.api.libs.json.Json

import keemun.controllers.{fetchRepositories, JsonBackend}
import keemun.models.AccountType


object Statistics extends Controller with JsonBackend {

  def statistics = Action.async{ implicit request =>
    import play.api.libs.concurrent.Execution.Implicits._
    fetchRepositories() map {
      repos =>
        val accountSettings = keemun.Config.playAppInstance.sources.accountsSettings
        Ok(
          Json.obj(
            "repositories" -> Json.obj(
              "total" -> repos.length
            ),
            "sources" -> Json.obj(
              "total" -> accountSettings.length,
              "user" -> accountSettings.count(_.account.accountType == AccountType.User),
              "org" -> accountSettings.count(_.account.accountType == AccountType.Org)
            )
          )
        )
    } recover commonJsonRecover
  }

}
