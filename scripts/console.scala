// load this script in `play console` via
// :load scripts/console.scala
val app = new play.core.StaticApplication(new java.io.File("."))
import play.api.Play.current
val conf = current.configuration

import keemun.models._
val userAccount = GithubUser("user", Some("http://example.com/u.png"))
val orgAccount = GithubOrg("org", Some("http://example.com/o.png"))

val userAccountSettings = new AccountSettings(userAccount, includePrivateRepos = false)
val userAccountSettings2 = new AccountSettings(userAccount, includePrivateRepos = false)
val orgAccountSettings = new AccountSettings(orgAccount, includePrivateRepos = true)
val accountSettings = userAccountSettings :: orgAccountSettings :: Nil

val userRepoA = new Repo("a", userAccount, Some("some description a"))
val userRepoB = new Repo("b", userAccount, Some("some description b"))
val userRepos = userRepoA :: userRepoB :: Nil

val orgRepoZ = new Repo("z", orgAccount, Some("some description z"), isPrivate = Some(true))
val orgRepoY = new Repo("y", userAccount, Some("some description y"), isPrivate = Some(false))
val orgRepos = orgRepoZ :: orgRepoY :: Nil

val repos = userRepos ++ userRepos

import play.api.libs.concurrent.Execution.Implicits._
import scala.concurrent.duration._
import scala.concurrent._
