package com.typedlabs.coinpayments4s.models

case class Coin(
  isFiat: Boolean,
  rateBtc: Double,
  lastUpdate: Long,
  txFee: Double,
  status: String,
  name: String,
  confirms: Long,
  canConvert: Boolean,
  capabilities: List[String]
)

object Coin {

  import play.api.libs.json._
  import play.api.libs.functional.syntax._

  //-- JSON
  implicit val reads: Reads[Coin] = (
    (__ \ "is_fiat").read[Int].map[Boolean](x => if(x == 1) true else false) and
    (__ \ "rate_btc").read[String].map[Double](_.toDouble) and
    (__ \ "last_update").read[String].map[Long](_.toLong) and 
    (__ \ "tx_fee").read[String].map[Double](_.toDouble) and 
    (__ \ "status").read[String] and
    (__ \ "name").read[String] and 
    (__ \ "confirms").read[String].map[Int](_.toInt) and
    (__ \ "can_convert").read[Int].map[Boolean](x => if(x == 1) true else false ) and
    (__ \ "capabilities").read[List[String]]
  )((isFiat, rateBtc, lastUpdate, txFee, status, name, confirms, canConvert, capabilities) => Coin(isFiat, rateBtc, lastUpdate, txFee, status, name, confirms, canConvert, capabilities))

}