import de.johoop.jacoco4sbt._
import JacocoPlugin._

name := "gagnechris"

version := "0.1"

scalaVersion := "2.10.3"

scalacOptions := Seq("-unchecked", "-deprecation", "-encoding", "utf8")

resolvers ++= Seq(
  "spray repo" at "http://repo.spray.io/"
)

libraryDependencies ++= {
  val sprayVersion = "1.2.0"
  val akkaVersion = "2.2.3"
  Seq(
    "io.spray" % "spray-can" % sprayVersion,
    "io.spray" % "spray-routing" % sprayVersion,
    "io.spray" % "spray-caching" % sprayVersion,
    "io.spray" % "spray-testkit" % sprayVersion,
    "io.spray" % "spray-client" % sprayVersion,
    "io.spray" %%  "spray-json" % "1.2.5",
    "com.typesafe.akka" %% "akka-actor" % akkaVersion,
    "com.typesafe.akka" %% "akka-slf4j" % akkaVersion,
    "com.typesafe.akka" %% "akka-testkit" % akkaVersion % "test",
    "ch.qos.logback" % "logback-classic" % "1.0.12",
    "org.scalatest" %% "scalatest" % "2.0" % "test",
    "commons-codec" % "commons-codec" % "1.8"
  )
}

seq(Revolver.settings: _*)

atmosSettings

jacoco.settings