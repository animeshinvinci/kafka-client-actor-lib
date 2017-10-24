name := "kafka-client-actor-lib"

version := "1.0"

scalaVersion := "2.12.3"

resolvers ++= Seq(
  "confluent" at "http://packages.confluent.io/maven/")

libraryDependencies  ++= Seq( "org.apache.kafka" % "kafka_2.12" % "0.11.0.1" ,
  "com.typesafe.akka" % "akka-actor_2.12" % "2.5.6" ,
  "com.typesafe.akka" % "akka-slf4j_2.12" % "2.5.6" ,
  "org.apache.avro" % "avro" % "1.8.2" ,
  "io.confluent" % "kafka-schema-registry-client" % "3.3.0" ,
  "io.confluent" % "kafka-avro-serializer" % "3.3.0",
  "io.confluent" % "kafka-streams-avro-serde" % "3.3.0",
  "com.sksamuel.avro4s" %% "avro4s-core" % "1.6.4" ,
  "org.apache.kafka" % "kafka-streams" % "0.11.0.1"

)