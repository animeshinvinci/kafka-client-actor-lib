package io.taps.kafka

import java.util.{Properties, UUID}

import com.typesafe.config.ConfigFactory
import kafka.utils.VerifiableProperties
import org.apache.kafka.clients.producer.{KafkaProducer, ProducerConfig, ProducerRecord}
import org.apache.avro.Schema
import org.apache.avro.generic.{GenericData, GenericRecord}
import kafka.consumer.{Consumer, ConsumerConfig}
import io.confluent.kafka.serializers.{KafkaAvroDecoder, KafkaAvroSerializer}


class KafkaPublisher {


  val broker = "localhost:9092"
  val zookeeper = "localhost:2181"
  val schemaRepo = "http://localhost:8081"

  val conf = ConfigFactory.load

  val producerProps = new Properties()
  producerProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, broker)
  producerProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, classOf[KafkaAvroSerializer])
  producerProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, classOf[KafkaAvroSerializer])
  producerProps.put("schema.registry.url", schemaRepo)

  val producer = new KafkaProducer[Object, Object](producerProps)


  def publish (topic: String, value: Object) ={
    val rec = new ProducerRecord[Object,Object](topic,UUID.randomUUID() , Object)
    producer.send(rec)

  }




  def publishTransactional(topic: String, value: Object) = {

    producer.beginTransaction()

    producer.commitTransaction()

  }

  def close: Unit = {
    producer.close()
  }

}
