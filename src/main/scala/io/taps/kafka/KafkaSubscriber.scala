package io.taps.kafka
import java.util
import java.util.{Properties, UUID}

import io.confluent.kafka.serializers.KafkaAvroSerializer
import org.apache.kafka.clients.consumer.{ConsumerConfig, ConsumerRecords, KafkaConsumer}
import org.apache.kafka.common.requests.IsolationLevel




class KafkaSubscriber(group:String,topic:String) {


  val consumerProps = new Properties()
  consumerProps.put("bootstrap.servers", "localhost:9092")
  consumerProps.put("group.id", group+)
  consumerProps.put("enable.auto.commit", "false")
  consumerProps.put("key.deserializer", classOf[KafkaAvroSerializer])
  consumerProps.put("value.deserializer", classOf[KafkaAvroSerializer])



  val consumerTxnProps = new Properties()
  consumerTxnProps.put("bootstrap.servers", "localhost:9092")
  consumerTxnProps.put("group.id", group+)
  consumerTxnProps.put("enable.auto.commit", "false")
  consumerTxnProps.put("key.deserializer", classOf[KafkaAvroSerializer])
  consumerTxnProps.put("value.deserializer", classOf[KafkaAvroSerializer])
  consumerTxnProps.put(ConsumerConfig.ISOLATION_LEVEL_CONFIG, IsolationLevel.READ_COMMITTED)




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
