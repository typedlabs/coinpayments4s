package com.typedlabs.coinpayments4s.models

case class TransactionRequest(
  amount: Double,
  buyerEmail: String,
  currency1: String = "EUR",
  currency2: String = "BTC"  
)

case class TransactionResponse(
  amount: Double,
  address: String,
  txnId: String,
  confirmsNeeded: Int,
  timeout: Int,
  statusUrl: String,
  qrcodeUrl: String
)

object TransactionResponse {
  
  import play.api.libs.json._
  import play.api.libs.functional.syntax._

  //-- JSON
  implicit val reads: Reads[TransactionResponse] = (
    (__ \ "amount").read[String].map[Double](_.toDouble) and 
    (__ \ "address").read[String] and
    (__ \ "txn_id").read[String] and
    (__ \ "confirms_needed").read[String].map[Int](_.toInt) and     
    (__ \ "timeout").read[Int] and
    (__ \ "status_url").read[String] and 
    (__ \ "qrcode_url").read[String]
  )(TransactionResponse.apply _)

}