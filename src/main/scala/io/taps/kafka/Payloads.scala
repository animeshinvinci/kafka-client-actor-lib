package io.taps.kafka

trait Payload

//Define Schema for order,price etc.

case class OrderPayload(name: String) extends Payload

case class OrderPayloadResponse(name: String) extends Payload