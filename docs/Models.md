# Models

<a name="github_repo"/>
## Repository

* Repo
 * isPrivate: Boolean
 * name
 * owner: GithubAccount
 * url
 * fullName
 * description
 * primaryLang: Option[ProgrammingLang]
 * langsStat: Option[Seq[ProgrammingLang]]
 * langsStatIndex: Option[Map[langCode: String, ProgrammingLang]]

<a name="prog_lang"/>
## Programming Language

* ProgrammingLang
 * code: String

* ProgrammingLangStat
 * lang: ProgrammingLang
 * extensions: Seq[String]
 * bytes: Option[Long]

<a name="account" />
## Account

* Account
 * name
 * webProfileUrl
 * avatarUrl
 * getRepoUrl(r: Repo)

* _case class_ GithubUser
* _case class_ GIthubOrg
