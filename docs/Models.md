# Models

<a name="github_repo"/>
## Repository

* Repo
 * isPrivate: Boolean
 * name
 * url
 * fullName
 * description
 * primaryLang: Option[ProgrammingLang]
 * langsStat: Option[Map[langCode: String, ProgrammingLang]]

<a name="prog_lang"/>
## Programming Language

* ProgrammingLang
 * code: String

* ProgrammingLangStat
 * lang: ProgrammingLang
 * extensions: Seq[String]
 * bytes: Option[Long]

<a name="github_account" />
## GitHubAccount

* GitHubAccount
 * name
 * profileUrl
 * avatarUrl
 * _private_ repoBaseUrl
 * getRepoUrl(r: Repo)

* _case class_ GitHubUser
* _case class_ GItHubCorp
