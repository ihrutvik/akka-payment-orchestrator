package com.hrutvik.payments

import akka.actor.typed.{ActorRef, ActorSystem, Behavior}
import akka.actor.typed.scaladsl.Behaviors
import com.hrutvik.payments.actors.AccountActor

object Main {

  private sealed trait GuardianCommand
  private final case class WrappedResponse(response: AccountActor.Response) extends GuardianCommand

  private def guardian(): Behavior[GuardianCommand] =
    Behaviors.setup { context =>
      val accountActor = context.spawn(AccountActor("account-123"), "account-123")

      val responseAdapter: ActorRef[AccountActor.Response] =
        context.messageAdapter(response => WrappedResponse(response))

      accountActor ! AccountActor.Charge(
        paymentId = "payment-001",
        amount = BigDecimal("49.99"),
        replyTo = responseAdapter
      )

      accountActor ! AccountActor.Refund(
        paymentId = "refund-001",
        amount = BigDecimal("10.00"),
        replyTo = responseAdapter
      )

      Behaviors.receiveMessage {
        case WrappedResponse(response) =>
          context.log.info("Payment response: {}", response)
          Behaviors.same
      }
    }

  def main(args: Array[String]): Unit = {
    ActorSystem(guardian(), "akka-payment-orchestrator")
  }
}
