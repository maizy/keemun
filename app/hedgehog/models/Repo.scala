package hedgehog.models

import play.api.libs.json._

/**
 * Copyright (c) Nikita Kovaliov, maizy.ru, 2013
 * See LICENSE.txt for details.
 */
class Repo (val name: String, val ownerName: String) {
  def url = s"$GITHUB_BASE_URL/$fullName"
  def fullName = s"$ownerName/$name"

  //FIXME stub
  def description =
    if (name(0).toLower == 'h') {
      Some("todo, some text will be here") }
    else {
      None
    }

  def isPrivate =
    if (name(0).toLower == 'g') {
      Some(true)
    } else if (name(0).toLower == 'e') {
      Some(false)
    } else {
      None
    }
}


object Repo {
  def getCurrentRepos = {
    //FIXME stub
    Seq(
      new Repo(name = "dev-setup", ownerName = "maizy"),
      new Repo(name = "hedGeHog", ownerName = "maizy"),
      new Repo(name = "scala-demo", ownerName = "maizy"),
      new Repo(name = "vnc-me", ownerName = "maizy"),
      new Repo(name = "PonyLib", ownerName = "maizy"),
      new Repo(name = "errserv", ownerName = "maizy"),
      new Repo(name = "myip", ownerName = "maizy"),
      new Repo(name = "venv-experiments", ownerName = "maizy"),
      new Repo(name = "WorkSprint", ownerName = "maizy"),
      new Repo(name = "giiit", ownerName = "maizy"),
      new Repo(name = "config", ownerName = "typesafehub"),
      new Repo(name = "underscore", ownerName = "jashkenas")
    )
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
