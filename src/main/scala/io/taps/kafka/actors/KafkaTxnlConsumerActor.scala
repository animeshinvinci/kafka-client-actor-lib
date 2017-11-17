package io.taps.kafka.actors

import akka.actor.{Actor, ActorLogging, Props}
import akka.event.Logging
import io.taps.kafka.{KafkaSubscriber, Message}

import scala.concurrent.duration.DurationInt


class KafkaTxnlConsumerActor (topic: String,group:String) extends Actor  with ActorLogging{
  import context.dispatcher

  import KafkaConsumerActor._
  val logger = Logging(context.system, this)

  val  subscriber = new KafkaSubscriber(topic,group);
  var consumer = subscriber.subscribeTransactional()


  val waitingTime = 1 seconds


  override def preStart(): Unit = {


  }

  def receive: Receive = {
    case Poll =>
      val records = consumer.poll(100)
      records.forEach { record =>
        val mess = record.value().asInstanceOf[Message]
        context.actorSelection(mess.origin) ! mess.payload
      }
      consumer.commitSync()

      if (records.isEmpty) {
        context.system.scheduler.scheduleOnce(waitingTime, self, Poll)
      } else {
        self ! Poll
      }
  }



}

object KafkaTxnlConsumerActor {

  def props(topic: String,group:String): Props = Props(classOf[KafkaTxnlConsumerActor], topic, group)

  case object Poll

}