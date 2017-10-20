package io.taps.kafka.actors

import akka.actor.{Actor, ActorRef, Props,ActorLogging}

import scala.concurrent.duration.DurationInt

class KafkaConsumerActor(consumer: Consumer, workers: ActorRef )extends Actor  with ActorLogging{

  override def receive: Receive = ???
}


object KafkaConsumerActor {

  def props(consumer: Consumer, workers: ActorRef): Props = Props(classOf[KafkaConsumerActor], consumer, workers)

  case object Read

}