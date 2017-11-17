import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import com.typesafe.config.ConfigFactory
import io.taps.kafka.actors.{KafkaConsumerActor, KafkaProducerActor, KafkaTxnlConsumerActor}

object MainServer extends App {

  implicit val system = ActorSystem()
  implicit val materializer = ActorMaterializer()
  implicit val executionContext = system.dispatcher

  val topic = "test-kafka"
  val group = "test-kafka-group"

  val consumerActor = system.actorOf(KafkaConsumerActor.props(topic,group))

  val consumerTxnActor = system.actorOf(KafkaTxnlConsumerActor.props(topic,group))

  val producerActor = system.actorOf(KafkaProducerActor.props)



}
