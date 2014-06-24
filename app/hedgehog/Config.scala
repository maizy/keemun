package hedgehog

import play.api.{Configuration, Play}

/**
 * Main project config used for wrapping app.configuration,
 * store hardcoded values and for other config builders.
 *
 * Copyright (c) Nikita Kovaliov, maizy.ru, 2014
 * See LICENSE.txt for details.
 */
class Config (appConfiguration: Configuration) {
  val apiBaseUrl = appConfiguration.getString("github.api_url")
                   .getOrElse("https://api.github.com").stripSuffix("/")
  lazy val githubClientConfig = new hedgehog.clients.github.Config(apiBaseUrl)


  val version = "0.0.1"  //TODO: sync with build.sbt (generate on project builded)
  val projectRepoUrl = "https://github.com/maizy/hedGeHog"
  val copyrightYears = "2013-2014"
}


object Config {
  val playAppInstance = new Config(Play.current.configuration)
}
