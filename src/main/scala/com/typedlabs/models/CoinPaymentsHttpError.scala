package com.typedlabs.coinpayments4s.models

sealed trait CoinPaymentsResponse
case class CoinPaymentsHttpError(error: String) extends CoinPaymentsResponse

object CoinPaymentsHttpError {
  
  import play.api.libs.json.Json

  implicit val httpErrorFmt = Json.format[CoinPaymentsHttpError]

}
