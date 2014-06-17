package hedgehog

import play.utils.UriEncoding.encodePathSegment
/**
 * Copyright (c) Nikita Kovaliov, maizy.ru, 2014
 * See LICENSE.txt for details.
 */
object UrlUtils {

  def urlEncodePathSegment(segment: String) {
    encodePathSegment(segment, "utf-8")
  }
  
  def urlEncode(param: String) {
    java.net.URLEncoder.encode(param, "utf-8")
  }
}
