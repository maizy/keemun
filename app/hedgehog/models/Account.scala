package hedgehog.models

import play.api.libs.json.{Json, JsValue, Writes}
import hedgehog.UrlUtils.urlEncodePathSegment


/**
 * Copyright (c) Nikita Kovaliov, maizy.ru, 2014
 * See LICENSE.txt for details.
 */
object AccountType extends Enumeration {
  type AccountType = Value
  val Org = Value("org")
  val User = Value("user")
}


trait Account {

  def name: String

  def accountType: AccountType.AccountType

  def avatarUrl: Option[String] = None

  protected def baseWebUrl: String = GITHUB_BASE_WEB_URL

  def webProfileUrl: String =
    s"$baseWebUrl/${urlEncodePathSegment(this.name)}"

  def getRepoUrl(repo: Repo): String =
    s"$webProfileUrl/${urlEncodePathSegment(repo.fullName)}"
}


object AccountJson {
  implicit val accountWrites = new Writes[Account] {
    def writes(account: Account): JsValue = {
      Json.obj(
        "id" -> account.name,
        "name" -> account.name,
        "profile_url" -> account.webProfileUrl,
        "type" -> account.accountType.toString,
        "avatar_url" -> account.avatarUrl
      )
    }
  }
}


case class GithubUser(
    name: String,
    override val avatarUrl: Option[String] = None)
  extends Account {

  val accountType = AccountType.User
}


case class GithubOrg(
    name: String,
    override val avatarUrl: Option[String] = None)
  extends Account {

  val accountType = AccountType.Org
}
