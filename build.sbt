name := """scala-filter-conflation"""

scalaVersion := "2.11.7"
lazy val akkaVersion = "2.4.4"

resolvers ++= Seq(
  "Scalaz Bintray Repo" at "https://dl.bintray.com/scalaz/releases",
  Resolver.url("Typesafe Ivy releases", url("https://repo.typesafe.com/typesafe/ivy-releases"))(Resolver.ivyStylePatterns)
)

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-actor" % akkaVersion,
  "com.typesafe.akka" %% "akka-slf4j" % akkaVersion,
  "com.typesafe.akka" %% "akka-stream" % akkaVersion,
  "io.kamon" %% "kamon-core" % "0.6.0",
  "io.kamon" %% "kamon-akka" % "0.6.0",
  "io.kamon" %% "kamon-statsd" % "0.6.0",
  "io.kamon" %% "kamon-autoweave" % "0.6.0",

  "org.scalatest" %% "scalatest" % "2.2.4" % "test",
  "com.typesafe.akka" %% "akka-testkit" % akkaVersion,
  "com.jayway.awaitility" % "awaitility" % "1.7.0" % "test"
)

