package keemun.models

import play.api.libs.json.{Writes, Json, JsValue}

/**
 * Copyright (c) Nikita Kovaliov, maizy.ru, 2014
 * See LICENSE.txt for details.
 *
 */
case class ProgrammingLang(code: String)


case class ProgrammingLangStat(
    lang: ProgrammingLang,
    extensions: Seq[String],
    bytes: Option[Long])


object ProgrammingLangJson {
  implicit val programmingLangWrites = new Writes[ProgrammingLang] {
    def writes(l: ProgrammingLang): JsValue = Json.obj(
      "id" -> l.code.toLowerCase,
      "name" -> l.code
    )
  }
}
