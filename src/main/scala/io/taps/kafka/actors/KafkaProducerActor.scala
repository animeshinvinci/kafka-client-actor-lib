package io.taps.kafka.actors

import akka.actor.{Actor, ActorLogging}
import akka.event.Logging
import io.taps.kafka.KafkaPublisher

case class publish(topic: String, value: Object,isTransactional:Boolean)

class KafkaProducerActor extends Actor with ActorLogging{

  val logger = Logging(context.system, this)

  val  kafkaPublisher = new KafkaPublisher

  override def receive: Receive =  {


    case publish(topic: String, value: Object, isTransactional: Boolean) =>
      if (isTransactional == true ){
        kafkaPublisher.publishTransactional(topic, value)
      }else {
        kafkaPublisher.publish(topic, value)
      }


  }

  override def postStop(): Unit = {
    super.postStop()
    kafkaPublisher.close
  }


}
