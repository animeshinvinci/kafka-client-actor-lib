package io.taps.kafka.actors

import akka.actor.{Actor, ActorLogging}
import io.taps.kafka.KafkaPublisher

class KafkaProducerActor extends Actor with ActorLogging{

 val  kafkaPublisher = new KafkaPublisher

  override def receive: Receive = {
    case (topic: String, value: Object) =>
      kafkaPublisher.publish(topic, value)

    case (topic: String, value: Object, isTransactional: Boolean) =>
      kafkaPublisher.publishTransactional(topic, value)

  }



}


