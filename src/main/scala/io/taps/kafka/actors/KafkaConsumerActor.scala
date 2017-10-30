package io.taps.kafka.actors

import akka.actor.{Actor, ActorLogging, ActorRef, Props}
import io.taps.kafka.KafkaSubscriber
import org.apache.kafka.common.serialization.ByteArrayDeserializer

import scala.concurrent.duration.DurationInt

class KafkaConsumerActor(consumer: KafkaSubscriber, workers: ActorRef )extends Actor  with ActorLogging{

  override def receive: Receive = Actor.emptyBehavior

  override def preStart(): Unit = {
    val consumerSettings = ConsumerSettings(as, new ByteArrayDeserializer, new StringDeserializer)
      .withBootstrapServers("kafka:9092")
      .withGroupId("group1")
      .withProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest")

    val done =
      Consumer.committableSource(consumerSettings, Subscriptions.topics("order_commands"))
        .mapAsync(1) { msg =>
          decode[Message[OrderPayloadResponse]](msg.record.value) map { m => context.actorSelection(m.origin) ! m.payload
          }
          Future.successful(msg)
        }
        .mapAsync(1) { msg =>
          msg.committableOffset.commitScaladsl()
        }
        .runWith(Sink.ignore)
  }
}


object KafkaConsumerActor {

  def props(consumer: KafkaSubscriber, workers: ActorRef): Props = Props(classOf[KafkaConsumerActor], consumer, workers)

  case object Read

}