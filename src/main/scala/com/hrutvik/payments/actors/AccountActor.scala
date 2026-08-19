package com.hrutvik.payments.actors

import akka.actor.typed.{ActorRef, Behavior}
import akka.actor.typed.scaladsl.Behaviors

object AccountActor {

  sealed trait Command

  final case class Charge(
      paymentId: String,
      amount: BigDecimal,
      replyTo: ActorRef[Response]
  ) extends Command

  final case class Refund(
      paymentId: String,
      amount: BigDecimal,
      replyTo: ActorRef[Response]
  ) extends Command

  sealed trait Response
  final case class Accepted(paymentId: String, message: String) extends Response
  final case class Rejected(paymentId: String, reason: String) extends Response

  def apply(accountId: String): Behavior[Command] =
    Behaviors.receive { (context, message) =>
      message match {
        case Charge(paymentId, amount, replyTo) if amount > 0 =>
          context.log.info(
            "Charge accepted: accountId={}, paymentId={}, amount={}",
            accountId,
            paymentId,
            amount
          )
          replyTo ! Accepted(paymentId, s"Charge of $amount accepted for $accountId")
          Behaviors.same

        case Charge(paymentId, amount, replyTo) =>
          replyTo ! Rejected(paymentId, s"Charge amount must be positive, received: $amount")
          Behaviors.same

        case Refund(paymentId, amount, replyTo) if amount > 0 =>
          context.log.info(
            "Refund accepted: accountId={}, paymentId={}, amount={}",
            accountId,
            paymentId,
            amount
          )
          replyTo ! Accepted(paymentId, s"Refund of $amount accepted for $accountId")
          Behaviors.same

        case Refund(paymentId, amount, replyTo) =>
          replyTo ! Rejected(paymentId, s"Refund amount must be positive, received: $amount")
          Behaviors.same
      }
    }
}
