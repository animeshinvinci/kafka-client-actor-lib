package io.taps.kafka
import java.util
import java.util.{Properties, UUID}

import com.typesafe.config.ConfigFactory
import io.confluent.kafka.serializers.KafkaAvroDeserializer
import org.apache.kafka.clients.consumer.{ConsumerConfig, ConsumerRecords, KafkaConsumer}
import org.apache.kafka.common.requests.IsolationLevel




class KafkaSubscriber(group:String,topic:String) {


  val conf = ConfigFactory.load

  val consumerProps = new Properties()
  consumerProps.put("bootstrap.servers", s"${conf.getString("kafka.bootstrap-servers")}")
  consumerProps.put("group.id", group)
  consumerProps.put("enable.auto.commit", "false")
  consumerProps.put("key.deserializer", classOf[KafkaAvroDeserializer])
  consumerProps.put("value.deserializer", classOf[KafkaAvroDeserializer])
  consumerProps.put("schema.registry.url", conf.getString("kafka.schema-registry-url"))



  val consumerTxnProps = new Properties()
  consumerTxnProps.put("bootstrap.servers", s"${conf.getString("kafka.bootstrap-servers")}")
  consumerTxnProps.put("group.id", group)
  consumerTxnProps.put("enable.auto.commit", "false")
  consumerTxnProps.put("key.deserializer", classOf[KafkaAvroDeserializer])
  consumerTxnProps.put("value.deserializer", classOf[KafkaAvroDeserializer])
  consumerTxnProps.put(ConsumerConfig.ISOLATION_LEVEL_CONFIG, IsolationLevel.READ_COMMITTED)
  consumerProps.put("schema.registry.url", conf.getString("kafka.schema-registry-url"))




  def subscribe() ={
    val consumer = new KafkaConsumer[Object, Object](consumerProps);
    consumer.subscribe(util.Arrays.asList(topic))
    consumer
  }


  def subscribeTransactional() ={
    val consumer = new KafkaConsumer[Object, Object](consumerTxnProps);
    consumer.subscribe(util.Arrays.asList(topic))
    consumer
  }
}
