import com.typesafe.sbt.SbtNativePackager._
import NativePackagerKeys._
import com.typesafe.sbt.SbtGit._

name := "keemun"

versionWithGit

git.baseVersion := "0.0.1"

scalaVersion := "2.11.1"

libraryDependencies ++= Seq(
  cache,
  "org.scalatest" % "scalatest_2.11" % "2.2.1" % "test",
  "com.github.nscala-time" %% "nscala-time" % "1.4.0",
  "com.netaporter" %% "scala-uri" % "0.4.3",
  ws,
  // frontend libs
  "org.webjars" %% "webjars-play" % "2.3.0",
  "org.webjars" % "bootstrap" % "3.0.0",
  "org.webjars" % "jquery" % "2.1.1",
  "org.webjars" % "lodash" % "2.4.1-6"
)

maintainer := "Nikita Kovaliov <nikita@maizy.ru>"

packageSummary := "github search tool"

linuxEtcDefaultTemplate := (baseDirectory.value / "debian" / "etc-default").asURL

debianPackageDependencies in Debian ++= Seq("oracle-java7-jre|openjdk-7-jre")

packageDescription := scala.io.Source.fromFile(baseDirectory.value / "README.md").getLines().mkString

lazy val root = (project in file(".")).enablePlugins(PlayScala)
