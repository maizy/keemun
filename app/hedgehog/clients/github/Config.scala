package hedgehog.clients.github

import play.api.Application

/**
 * Copyright (c) Nikita Kovaliov, maizy.ru, 2014
 * See LICENSE.txt for details.
 */

class Config(val apiBaseUrl: String)

object Config {
  def fromPlayApp(app: Application) = {
    val baseUrl = app.configuration.getString("github.api_url")
      .getOrElse("https://api.github.com").stripSuffix("/")
    new Config(baseUrl)
  }
}
