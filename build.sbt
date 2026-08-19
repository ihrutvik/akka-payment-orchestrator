ThisBuild / scalaVersion := "2.13.18"
ThisBuild / organization := "com.hrutvik"
ThisBuild / version := "0.1.0-SNAPSHOT"

lazy val akkaVersion = "2.6.20"

lazy val root = (project in file("."))
  .settings(
    name := "akka-payment-orchestrator",
    libraryDependencies ++= Seq(
      "com.typesafe.akka" %% "akka-actor-typed" % akkaVersion,
      "com.typesafe.akka" %% "akka-slf4j" % akkaVersion,
      "ch.qos.logback" % "logback-classic" % "1.5.18",
      "com.typesafe.akka" %% "akka-actor-testkit-typed" % akkaVersion % Test,
      "org.scalatest" %% "scalatest" % "3.2.19" % Test
    )
  )
