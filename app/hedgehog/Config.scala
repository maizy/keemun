package hedgehog

import scala.concurrent.ExecutionContext
import play.api.{Configuration, Play}
import hedgehog.clients.github
import hedgehog.models.Sources

/**
 * Main project config used for wrapping app.configuration,
 * store hardcoded values and for other config builders.
 *
 * Copyright (c) Nikita Kovaliov, maizy.ru, 2014
 * See LICENSE.txt for details.
 */
class Config (appConfiguration: Configuration, asyncContext: ExecutionContext) {
  val apiBaseUrl = appConfiguration.getString("github.api_url")
                   .getOrElse("https://api.github.com").stripSuffix("/")
  lazy val githubClientConfig = new hedgehog.clients.github.Config(apiBaseUrl)
  lazy val githubClient = new github.Client(githubClientConfig, asyncContext) //TODO: is there good place for that?


  val version = "0.0.1"  //TODO: sync with build.sbt (generate on project builded)
  val projectRepoUrl = "https://github.com/maizy/hedGeHog"
  val copyrightYears = "2013-2014"
  lazy val sources = new Sources(appConfiguration)
}


object Config {
  val playAppInstance = new Config(
      Play.current.configuration,
      play.api.libs.concurrent.Execution.Implicits.defaultContext)
}
