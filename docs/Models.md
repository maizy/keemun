# Models

_draft_

## Structure

### Group

* Group
 * name: String
 * source: Source
 * reps: Map[repoCode: String, Repo]

### Repo

* Repo
 * isPrivate: Option[Boolean] :question:
 * name
 * url
 * fullName
 * langs:  Option[Map[langCode: String, ProgrammingLang]]
 * primaryLang: Option[ProgrammingLang]

### Languages

* ProgrammingLang
 * code: String

* ProgrammingLangStat
 * lang: ProgrammingLang
 * extensions: Seq[String]
 * bytes: Option[Long]

### Sources

* SourceBase (_or std postfix for Base classes_)
 * code: String

*  _case class_ SourceConfig
 * ...

*  _case class_ SourceApi
 * ...

### GitHubAccount

* GitHubAccount
 * name
 * profileUrl
 * avatarUrl
 * _private_ repoBaseUrl
 * getRepoUrl(r: Repo)

* _case class_ GitHubUser
* _case class_ GItHubCorp

### Builders
* buildGroupsFromApplicationConfig() :question:
* buildRepsFromApplicationConfig() :question:
* Repo.fromConfig(s: Source, ...) :question:
* Group.fromConfig(s: Source, ...) :question:
