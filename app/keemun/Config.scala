package keemun

import play.api.libs.ws.WSClient
import play.api.libs.ws.WS

import scala.concurrent.ExecutionContext
import play.api.{Configuration, Play}
import keemun.clients.github
import keemun.models.Sources

/**
 * Main project config used for wrapping app.configuration,
 * store hardcoded values and for other config builders.
 *
 * Copyright (c) Nikita Kovaliov, maizy.ru, 2014
 * See LICENSE.txt for details.
 */
class Config(appConfiguration: Configuration, asyncContext: ExecutionContext, val commonHttpClient: WSClient) {
  val apiBaseUrl = appConfiguration.getString("github.api_url")
                   .getOrElse("https://api.github.com").stripSuffix("/")
  lazy val githubClientConfig = new keemun.clients.github.Config(
    apiBaseUrl,
    accessToken = appConfiguration.getString("github.app_token"),
    commonHttpClient
  )
  lazy val githubClient = new github.Client(githubClientConfig, asyncContext) //TODO: is there good place for that?


  val version = keemun.Version.SYMBOLIC
  val projectRepoUrl = "https://github.com/maizy/keemun"
  val copyrightYears = "2013-2014"
  lazy val sources = new Sources(appConfiguration)
}


object Config {
  val playAppInstance = new Config(
      Play.current.configuration,
      play.api.libs.concurrent.Execution.Implicits.defaultContext,
      commonHttpClient = WS.client(Play.current)
  )
}
