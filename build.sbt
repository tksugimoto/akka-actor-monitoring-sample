name := """akka-actor-takipi-sample"""

version := "1.0"

scalaVersion := "2.11.6"

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-actor" % "2.3.11",
  "com.typesafe.akka" %% "akka-testkit" % "2.3.11" % "test",
  "org.scalatest" %% "scalatest" % "2.2.4" % "test")

resolvers += "takipi-sdk" at "https://dl.bintray.com/takipi/maven"
libraryDependencies += "com.typesafe.cinnamon" %% "cinnamon-chmetrics-statsd-reporter" % "1.2.1"
