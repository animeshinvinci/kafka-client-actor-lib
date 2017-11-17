package io.taps.kafka

import java.util.{Properties, UUID}

import com.typesafe.config.ConfigFactory
import org.apache.kafka.clients.producer.{KafkaProducer, ProducerConfig, ProducerRecord}
import io.confluent.kafka.serializers.{KafkaAvroDecoder, KafkaAvroSerializer}
import org.apache.kafka.common.KafkaException
import org.apache.kafka.common.errors.AuthorizationException
import org.apache.kafka.common.errors.OutOfOrderSequenceException
import org.apache.kafka.common.errors.ProducerFencedException


class KafkaPublisher {



  val conf = ConfigFactory.load

  val producerProps = new Properties()
  producerProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, s"${conf.getString("kafka.bootstrap-servers")}")
  producerProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, classOf[KafkaAvroSerializer])
  producerProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, classOf[KafkaAvroSerializer])
  producerProps.put("schema.registry.url", conf.getString("kafka.schema-registry-url"))



  val producer = new KafkaProducer[Object, Object](producerProps)



  val  producerTxnProps = new Properties()
  producerTxnProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, s"${conf.getString("kafka.bootstrap-servers")}")
  producerTxnProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, classOf[KafkaAvroSerializer])
  producerTxnProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, classOf[KafkaAvroSerializer])
  producerTxnProps.put("schema.registry.url", conf.getString("kafka.schema-registry-url"))
  producerTxnProps.put(ProducerConfig.TRANSACTIONAL_ID_CONFIG, UUID.randomUUID().toString)
  producerTxnProps.put(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, "true")


  val txnproducer = new KafkaProducer[Object, Object](producerTxnProps)


  def publish (topic: String, value: Object) ={
    val rec = new ProducerRecord[Object,Object](topic,UUID.randomUUID() , value)
    producer.send(rec)

  }




  def publishTransactional(topic: String, value: Object) = {

    producer.initTransactions()

    try {
      producer.beginTransaction()
        producer.send(new ProducerRecord[Object, Object](topic,UUID.randomUUID() , value))
      producer.commitTransaction()
    } catch {
      case e@(_: ProducerFencedException | _: OutOfOrderSequenceException | _: AuthorizationException) =>
        producer.close()
      case e: KafkaException =>

        producer.abortTransaction()
    }

  }

  def close: Unit = {
    producer.close()
  }

}
