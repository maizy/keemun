package hedgehog.models

import hedgehog.UrlUtils._

/**
 * Copyright (c) Nikita Kovaliov, maizy.ru, 2014
 * See LICENSE.txt for details.
 */
trait GithubAccount {

  def name: String

  def avatarUrl: Option[String] = None

  protected def baseWebUrl: String = GITHUB_BASE_WEB_URL

  def webProfileUrl: String =
    s"$baseWebUrl/${urlEncodePathSegment(name)}"

  def getRepoUrl(repo: Repo): String =
    s"$webProfileUrl/${urlEncodePathSegment(repo.fullName)}"
}


case class GithubUser(
    name: String,
    override val avatarUrl: Option[String])
  extends GithubAccount


case class GithubOrg(
    name: String,
    override val avatarUrl: Option[String])
  extends GithubAccount
