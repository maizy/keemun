package hedgehog.clients

import scala.util.matching.Regex
import com.netaporter.uri.Uri

import hedgehog.StringUtils.StringImplicitConverters

/**
 * Copyright (c) Nikita Kovaliov, maizy.ru, 2014
 * See LICENSE.txt for details.
 */
package object github {

  case class HeaderLink(linkType: String, link: String, page: Option[Int], perPage: Option[Int])

  private val headerSplitRegexp = """,\s""".r

  def parseLinkHeader(header: String): Map[String, HeaderLink] =
    //ex
    //<https://api.github.com/organizations/12345/repos?per_page=100&page=2>; rel="next", <https://...>; rel="last"
    (for {
      rel <- headerSplitRegexp.split(header)
      parsed <- parseRel(rel)
    } yield parsed.linkType -> parsed).toMap

  private val linkRegexp = new Regex("""<([^>]+)>;\s+rel="([^"]+)"""", "link", "linkType")

  def parseRel(rel: String): Option[HeaderLink] =
    //ex
    //<https://api.github.com/organizations/12345/repos?per_page=100&page=2>; rel="next"
    (linkRegexp findFirstMatchIn rel) map {
      relMatch: Regex.Match => {
        val link = relMatch.group("link")
        val parsedLink = Uri.parse(link)
        val linkType = relMatch.group("linkType")
        def paramToOptInt(paramName: String): Option[Int] = {
          parsedLink.query.param(paramName).last.toIntOpt
        }
        HeaderLink(linkType, link, paramToOptInt("page"), paramToOptInt("per_page"))
      }
    }
}
