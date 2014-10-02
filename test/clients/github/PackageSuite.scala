package test

import org.scalatest._

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

/**
 * Copyright (c) Nikita Kovaliov, maizy.ru, 2014
 * See LICENSE.txt for details.
 */
@RunWith(classOf[JUnitRunner])
class PackageSuite extends FunSuite with Matchers {

  import keemun.clients.github
  import keemun.clients.github.HeaderLink

  test("parseLinkHeader") {
    val header =
      """<https://api.github.com/organizations/12345/repos?per_page=100&page=2>; rel="next",
        |<https://api.github.com/organizations/12345/repos?per_page=100&page=5>; rel="last"""".stripMargin

    assert(
      github.parseLinkHeader(header) === Map(
        "next" -> HeaderLink(
          linkType = "next",
          link = "https://api.github.com/organizations/12345/repos?per_page=100&page=2",
          page = Some(2),
          perPage = Some(100)
        ),
        "last" -> HeaderLink(
          linkType = "last",
          link = "https://api.github.com/organizations/12345/repos?per_page=100&page=5",
          page = Some(5),
          perPage = Some(100)
        )
      )
    )
  }

  test("parseLinkHeader for bad formatted values") {
    assert(github.parseLinkHeader("") === Map())
    assert(github.parseLinkHeader(", , , , ") === Map())
    assert(
      github.parseLinkHeader(
        """<https://api.github.com/organizations/12345/repos?per_page=100&page=2>; rel="next", bubuub"""
      ) === Map(
        "next" -> HeaderLink(
          linkType = "next",
          link = "https://api.github.com/organizations/12345/repos?per_page=100&page=2",
          page = Some(2),
          perPage = Some(100)
        )
      ))
  }
}
