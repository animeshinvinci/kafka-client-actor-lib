package io.taps.kafka

case class Message[A <: Payload](origin: String, payload: A)

