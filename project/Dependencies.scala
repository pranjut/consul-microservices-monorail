import sbt._

object Dependencies {

  val scala = "2.11.6"

  val resolvers = DefaultOptions.resolvers(snapshot = true) ++ Seq(
    "scalaz-releases" at "http://dl.bintray.com/scalaz/releases"
  )

  val playVersion = play.core.PlayVersion.current

  val jdbc = "com.typesafe.play" %% "play-jdbc" % playVersion
  val cache = "com.typesafe.play" %% "play-cache" % playVersion
  val specs2 = "com.typesafe.play" %% "play-specs2" % playVersion % "test"

  val kafka = "org.apache.kafka" %% "kafka" % "0.9.0.1"
  val consulClient = "com.codacy" %% "scala-consul" % "1.1.0"
  val ws = "com.typesafe.play" %% "play-ws" % playVersion
  val json = "com.typesafe.play" %% "play-json" % playVersion

  val playDependencies: Seq[ModuleID] = Seq(
    jdbc,
    cache,
    ws,
    json,
    specs2
  )

  val commonModuleDependencies: Seq[ModuleID] = Seq(kafka, consulClient, ws, json, specs2)
}
