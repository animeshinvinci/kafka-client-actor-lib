name := "kafka-client-actor-lib"

version := "1.0"

scalaVersion := "2.12.3"

resolvers ++= Seq(
  "confluent" at "http://packages.confluent.io/maven/")

libraryDependencies += "org.apache.kafka" % "kafka_2.12" % "0.11.0.1"
libraryDependencies += "com.typesafe.akka" % "akka-actor_2.12" % "2.5.6"
libraryDependencies += "com.typesafe.akka" % "akka-slf4j_2.12" % "2.5.6"
libraryDependencies += "org.apache.avro" % "avro" % "1.8.2"
libraryDependencies += "io.confluent" % "kafka-schema-registry-client" % "3.2.0"
libraryDependencies += "com.sksamuel.avro4s" %% "avro4s-core" % "1.6.4"
