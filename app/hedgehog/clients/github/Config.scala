package hedgehog.clients.github

import java.util.regex.Pattern
import play.api.libs.ws.WSClient

/**
 * Copyright (c) Nikita Kovaliov, maizy.ru, 2014
 * See LICENSE.txt for details.
 */

class Config(val apiBaseUrl: String, val accessToken: Option[String], val httpClient: WSClient) {

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
