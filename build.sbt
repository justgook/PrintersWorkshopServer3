//import sbt.Keys._
import sbt._


name := "akka-http-sangria"

version := "1.0"

scalaVersion := "2.12.1"

scalacOptions ++= Seq("-deprecation", "-feature")
libraryDependencies ++= Seq(
"com.typesafe.akka" %% "akka-http" % "10.0.1" ,
"com.typesafe.akka" %% "akka-http-spray-json" % "10.0.1",

"org.sangria-graphql" %% "sangria" % "1.0.0",
"org.sangria-graphql" %% "sangria-spray-json" % "0.3.2"

)

// Revolver.settings
// enablePlugins(JavaAppPackaging)