package hedgehog.clients.github

import java.util.regex.Pattern
import play.api.Application

/**
 * Copyright (c) Nikita Kovaliov, maizy.ru, 2014
 * See LICENSE.txt for details.
 */

class Config(val apiBaseUrl: String) {

  def replaceBaseUrl(url: String): String = {
    var processedUrl = url
    for (prefix <- List("https://", "http://")) {
      if (processedUrl.toLowerCase startsWith prefix) {
        processedUrl = processedUrl.substring(prefix.length)
      }
    }

    val postfixSlash = if (processedUrl endsWith "/") "/" else ""
    val parts = processedUrl.stripSuffix("/").split(Pattern.quote("/")).toList
    parts match {
      case List() => url
      case List(domain) => apiBaseUrl + postfixSlash
      case domain :: paths => apiBaseUrl +"/"+ paths.mkString("/") + postfixSlash
    }
  }
}

object Config {
  def fromPlayApp(app: Application) = {
    val baseUrl = app.configuration.getString("github.api_url")
      .getOrElse("https://api.github.com").stripSuffix("/")
    new Config(baseUrl)
  }
}
