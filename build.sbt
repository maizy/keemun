name := "hedgehog"

version := "0.0.1-SNAPSHOT"

scalaVersion := "2.11.1"

libraryDependencies ++= Seq(
  //jdbc,
  //anorm,
  cache,
  "org.scalatest" % "scalatest_2.11" % "2.2.1" % "test",
  "com.github.nscala-time" %% "nscala-time" % "1.4.0",
  "com.netaporter" %% "scala-uri" % "0.4.3",
  ws
)

lazy val root = (project in file(".")).enablePlugins(PlayScala)
