package keemun.clients.github

import play.api.libs.functional.syntax._
import play.api.libs.json.{Reads, __}

import keemun.models.{GithubOrg, Account, GithubUser, Repo, ProgrammingLang}
/**
 * Copyright (c) Nikita Kovaliov, maizy.ru, 2014
 * See LICENSE.txt for details.
 */
object GithubReades {

  val accountReads: Reads[Account] = (
    (__ \ "type").read[String] ~
    (__ \ "login").read[String] ~
    (__ \ "avatar_url").readNullable[String]
  ) apply {
    (accountType, login, avatarUrl) => accountType match {
      case "User" => GithubUser(login, avatarUrl)
      case "Organization" => GithubOrg(login, avatarUrl)
      case _ => throw new FormatError(s"Unknown owner type "+ accountType)
    }
  }

  val repoReads: Reads[Repo] = (
    (__ \ "name").read[String] ~
    (__ \ "owner").read[Account](accountReads) ~
    (__ \ "description").readNullable[String] ~
    (__ \ "private").read[Boolean] ~
    (__ \ "language").readNullable[String]
  ) apply {
    (name, owner, description, isPrivate, lang) => {
      Repo(
        name,
        owner,
        description = description,
        isPrivate = Some(isPrivate),
        primaryLang = lang.map(l => ProgrammingLang(l))
      )
    }
  }

  val repoSeqReads: Reads[Seq[Repo]] = Reads.seq[Repo](repoReads)
}
