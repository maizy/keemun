package hedgehog.models

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
