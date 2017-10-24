package io.taps.kafka.actors

import akka.actor.{Actor, ActorLogging, ActorRef, Props}
import io.taps.kafka.KafkaSubscriber

import scala.concurrent.duration.DurationInt

class KafkaConsumerActor(consumer: KafkaSubscriber, workers: ActorRef )extends Actor  with ActorLogging{

  override def receive: Receive = ???
}


object KafkaConsumerActor {

  def props(consumer: KafkaSubscriber, workers: ActorRef): Props = Props(classOf[KafkaConsumerActor], consumer, workers)

  case object Read

}