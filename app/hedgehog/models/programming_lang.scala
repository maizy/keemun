package hedgehog.models

/**
 * Copyright (c) Nikita Kovaliov, maizy.ru, 2014
 * See LICENSE.txt for details.
 *
 */
class ProgrammingLang(val code: String)


class ProgrammingLangStat(
    val lang: ProgrammingLang,
    val extensions: Seq[String],
    val bytes: Option[Long])
