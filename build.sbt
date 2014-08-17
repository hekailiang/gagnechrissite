name := "gagnechris"

version := "0.1"

scalaVersion := "2.11.2"

scalacOptions := Seq("-unchecked", "-deprecation", "-encoding", "utf8")

resolvers ++= Seq(
  "spray repo" at "http://repo.spray.io/"
)

libraryDependencies ++= {
  val sprayVersion = "1.3.1"
  val akkaVersion = "2.3.5"
  Seq(
    "io.spray" %% "spray-can" % sprayVersion,
    "io.spray" %% "spray-routing" % sprayVersion,
    "io.spray" %% "spray-caching" % sprayVersion,
    "io.spray" %% "spray-testkit" % sprayVersion % "test",
    "io.spray" %% "spray-client" % sprayVersion,
    "io.spray" %%  "spray-json" % "1.2.6",
    "com.typesafe.akka" %% "akka-actor" % akkaVersion,
    "com.typesafe.akka" %% "akka-slf4j" % akkaVersion,
    "com.typesafe.akka" %% "akka-testkit" % akkaVersion % "test",
    "ch.qos.logback" % "logback-classic" % "1.1.2",
    "org.scalatest" %% "scalatest" % "2.2.1" % "test",
    "org.mockito" % "mockito-all" % "1.9.5" % "test",
    "commons-codec" % "commons-codec" % "1.9"
  )
}

seq(Revolver.settings: _*)
