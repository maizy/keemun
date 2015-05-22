resolvers ++= Seq(
  "Typesafe repository" at "http://repo.typesafe.com/typesafe/releases/",
  "jgit-repo" at "http://download.eclipse.org/jgit/maven"
)

addSbtPlugin("com.typesafe.play" % "sbt-plugin" % "2.3.7")

addSbtPlugin("com.typesafe.sbt" % "sbt-git" % "0.6.4")

addSbtPlugin("com.typesafe.sbt" % "sbt-jshint" % "1.0.3")
