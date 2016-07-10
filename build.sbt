name := "squadbuilder-scala"

version := "v1-1.0"

scalaVersion := "2.11.8"

resolvers ++= Seq("RoundEights" at "http://maven.spikemark.net/roundeights")

libraryDependencies ++= Seq(
  // -- Logging --
  "ch.qos.logback" % "logback-classic" % "1.1.2"
  // -- Akka --
  , "com.typesafe.akka" %% "akka-actor" % "2.4.7"
  , "com.typesafe.akka" %% "akka-testkit" % "2.4.7"
  , "com.typesafe.akka" %% "akka-stream" % "2.4.7"
  , "com.typesafe.akka" %% "akka-http-core" % "2.4.7"
  , "com.typesafe.akka" %% "akka-http-spray-json-experimental" % "2.4.7"
  // -- Time --
  , "joda-time" % "joda-time" % "2.9.1"
  // -- MongoDB --
  , "org.reactivemongo" %% "reactivemongo" % "0.11.14"
  // -- Salt/Hash --
  , "com.roundeights" %% "hasher" % "1.2.0"
  // -- JWT --
  , "io.igl" %% "jwt" % "1.2.0"
)