name := "akka-cluster-sample"

version := "0.1"

scalaVersion := "2.12.8"

enablePlugins(JavaAppPackaging)
enablePlugins(UniversalPlugin)
enablePlugins(DockerPlugin)

mainClass in Compile := Some ("sample.cluster.EventMain")

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

  "com.lightbend.akka.management" %% "akka-management" % akkaManagementVersion,
  "com.lightbend.akka.management" %% "akka-management-cluster-http" % akkaManagementVersion,

  //test
  "org.scalatest" %% "scalatest" % "3.0.5" % Test, //scala's jUnit equivalent
  "org.scalacheck" %% "scalacheck" % "1.14.0" % Test, //property testing

  //logging
  "ch.qos.logback" % "logback-classic" % "1.2.3"
)