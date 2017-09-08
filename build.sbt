name := "scully"
version := "0.0.0"

scalaVersion := "2.12.1"

lazy val versions = new {
  val finch = "0.15.1"
  val circe = "0.8.0"
  val elastic = "5.4.10"
  val test = "3.0.0"
}

resolvers ++= Seq(
  Resolver.sonatypeRepo("releases"),
  "Twitter Maven" at "https://maven.twttr.com"
)

libraryDependencies ++= Seq(
  "com.github.finagle" %% "finch-core" % versions.finch,
  "com.github.finagle" %% "finch-generic" % versions.finch,
  "com.github.finagle" %% "finch-circe" % versions.finch,

  "com.sksamuel.elastic4s" %% "elastic4s-core" % versions.elastic,
  "com.sksamuel.elastic4s" %% "elastic4s-http" % versions.elastic,
  "com.sksamuel.elastic4s" %% "elastic4s-circe" % versions.elastic,

  "org.scalatest" %% "scalatest" % versions.test % "test",
  "com.sksamuel.elastic4s" %% "elastic4s-core" % versions.elastic % "test",
  "com.sksamuel.elastic4s" %% "elastic4s-embedded" % versions.elastic % "test"
)

enablePlugins(JavaAppPackaging)
enablePlugins(NewRelic)
