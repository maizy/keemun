name := "hedgehog"

version := "0.0.1-SNAPSHOT"

libraryDependencies ++= Seq(
  //jdbc,
  //anorm,
  //cache,
  "org.scalatest" % "scalatest_2.10" % "2.1.7" % "test",
  "com.github.nscala-time" %% "nscala-time" % "1.2.0",
  "com.netaporter" %% "scala-uri" % "0.4.2"
)

play.Project.playScalaSettings
