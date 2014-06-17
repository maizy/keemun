package hedgehog.models

import play.api.libs.json._
/**
 * Copyright (c) Nikita Kovaliov, maizy.ru, 2013-2014
 * See LICENSE.txt for details.
 */
class Repo(
    val name: String,
    val owner: GithubAccount,
    val description: Option[Boolean] = None,
    val isPrivate: Option[Boolean] = None,
    val primaryLang: Option[ProgrammingLang] = None,
    val langsStat: Option[Seq[ProgrammingLangStat]] = None) {
  def fullName = s"${owner.name}/$name"
  lazy val langsStatIndex = langsStat.map(seq => seq.map{stat => (stat.lang.code, stat)}.toMap)
  def url: String = owner.getRepoUrl(this)
}


object Repo {
  //FIXME
  def getCurrentRepos: Seq[Repo] = List()

  implicit val writer = new Writes[Repo] {
    def writes(r: Repo) : JsValue = {
      //FIXME: as documented
      Json.obj(
        "name" -> r.name,
        //"owner" -> r.ownerName,
        //"url" -> r.url,
        "full_name" -> r.fullName,
        "description" -> r.description,
        "is_private" -> r.isPrivate
      )
    }
  }
}
