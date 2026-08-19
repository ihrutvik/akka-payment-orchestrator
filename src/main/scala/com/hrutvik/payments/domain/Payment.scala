package com.hrutvik.payments.domain

sealed trait PaymentStatus
object PaymentStatus {
  case object Pending extends PaymentStatus
  case object Processing extends PaymentStatus
  case object Completed extends PaymentStatus
  case object Failed extends PaymentStatus
}

final case class Payment(
    id: String,
    accountId: String,
    amount: BigDecimal,
    status: PaymentStatus
)
