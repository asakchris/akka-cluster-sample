name := "akka-cluster-sample"

version := "0.1.1.1-SNAPSHOT"

scalaVersion := "2.12.6"

enablePlugins(JavaAppPackaging)
enablePlugins(UniversalPlugin)
enablePlugins(DockerPlugin)

dockerEntrypoint := Seq("bin/akka-cluster-sample")
dockerRepository := Some("asakchris")
dockerUpdateLatest := true
dockerExposedPorts := Seq(2551, 8558)

maintainer := "christopher.kamaraj@gmail.com"

lazy val akkaVersion = "2.5.21"
lazy val akkaManagementVersion = "1.0.0-RC3"

resolvers += Resolver.mavenLocal

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-actor" % akkaVersion,
  "com.typesafe.akka" %% "akka-slf4j" % akkaVersion,
  "com.typesafe.akka" %% "akka-testkit" % akkaVersion % Test,

  //cluster
  "com.typesafe.akka" %% "akka-cluster" % akkaVersion,
  "com.typesafe.akka" %% "akka-cluster-metrics" % akkaVersion,
  "com.typesafe.akka" %% "akka-cluster-tools" % akkaVersion,
  "com.typesafe.akka" %% "akka-multi-node-testkit" % akkaVersion,

  // Management
  "com.lightbend.akka.management" %% "akka-management" % akkaManagementVersion,
  "com.lightbend.akka.management" %% "akka-management-cluster-http" % akkaManagementVersion,

  // AWS API - ECS Discovery
  "com.lightbend.akka.management" %% "akka-management-cluster-bootstrap" % akkaManagementVersion,
  "com.lightbend.akka.discovery" %% "akka-discovery-aws-api-async" % akkaManagementVersion,
  "com.typesafe.akka" %% "akka-discovery" % akkaVersion,

  //test
  "org.scalatest" %% "scalatest" % "3.0.5" % Test, //scala's jUnit equivalent
  "org.scalacheck" %% "scalacheck" % "1.14.0" % Test, //property testing

  //logging
  "ch.qos.logback" % "logback-classic" % "1.2.3"
)