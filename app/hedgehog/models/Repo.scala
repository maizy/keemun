package hedgehog.models

import play.api.libs.json._
import play.api.Play.current
import scala.collection.JavaConverters._
/**
 * Copyright (c) Nikita Kovaliov, maizy.ru, 2013-2014
 * See LICENSE.txt for details.
 */
class Repo (val name: String, val ownerName: String) {
  def url = s"$GITHUB_BASE_URL/$fullName"
  def fullName = s"$ownerName/$name"

  //TODO
  def description: Option[String] = None
  def isPrivate: Option[Boolean] = None
}


object Repo {
  def getCurrentRepos: Seq[Repo] =
    current.configuration
      .getStringList("sources.repos")
      .map(_.asScala map Repo.fromFullName)
      .getOrElse(List())

  def fromFullName(fullName: String): Repo =
    fullName.split("/").toList match {
      case ownerName :: name :: Nil => new Repo(name, ownerName)
      case _ => throw new ConfigError("Bad repo name format for \""+ fullName +"\"")
    }

  implicit val writer = new Writes[Repo] {
    def writes(r: Repo) : JsValue = {
      Json.obj(
        "name" -> r.name,
        "owner" -> r.ownerName,
        "url" -> r.url,
        "full_name" -> r.fullName,
        "description" -> r.description,
        "is_private" -> r.isPrivate
      )
    }
  }
}
