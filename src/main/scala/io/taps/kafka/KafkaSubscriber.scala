package io.taps.kafka
import java.util.{Properties, UUID}

import org.apache.kafka.clients.consumer.{ConsumerRecords, KafkaConsumer}
import org.apache.kafka.common.errors.WakeupException
import org.apache.kafka.common.serialization.StringDeserializer
import org.slf4j.LoggerFactory



// have avro schema parser & converter utility
// Data prodcing and consuming should be  generic record with help from avro schema file .asvc
//


class KafkaSubscriber {



  def subscribe: Unit ={
    // Start an Actor with Kafka subscription
    //

  }


  def subscribeTransactional: Unit ={
    // Start  an actor for transactional mesaaging susbscription
    // user should provide only topic name- partition name should be acquired by itself.

  }
}
