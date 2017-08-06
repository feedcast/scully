name := "scully"
version := "0.0.0"

scalaVersion := "2.12.1"

lazy val versions = new {
  val finch = "0.15.1"
  val circe = "0.8.0"
  val redis = "2.7.2"
  val test = "3.0.0"
}

resolvers ++= Seq(
  Resolver.sonatypeRepo("releases"),
  "Twitter Maven" at "https://maven.twttr.com"
)

libraryDependencies ++= Seq(
  "io.circe" %% "circe-generic" % versions.circe,

  "com.github.finagle" %% "finch-core" % versions.finch,
  "com.github.finagle" %% "finch-generic" % versions.finch,
  "com.github.finagle" %% "finch-circe" % versions.finch,

  "org.scalatest" %% "scalatest" % versions.test % "test"
)

enablePlugins(JavaAppPackaging)
