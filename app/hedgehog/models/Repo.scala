package hedgehog.models

import play.api.libs.json._
/**
 * Copyright (c) Nikita Kovaliov, maizy.ru, 2013-2014
 * See LICENSE.txt for details.
 */
case class Repo(
    name: String,
    owner: Account,
    description: Option[String] = None,
    isPrivate: Option[Boolean] = None,
    primaryLang: Option[ProgrammingLang] = None,
    langsStat: Option[Seq[ProgrammingLangStat]] = None) {
  def fullName = s"${owner.name}/$name"
  lazy val langsStatIndex = langsStat.map(seq => seq.map{stat => (stat.lang.code, stat)}.toMap)
  def url: String = owner.getRepoUrl(this)
}


object Repo {

  implicit val repoWrites = new Writes[Repo] {
    def writes(r: Repo): JsValue = {
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
