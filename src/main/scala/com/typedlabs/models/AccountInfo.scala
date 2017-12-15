package com.typedlabs.coinpayments4s.models

case class AccountInfo(
  username: String,
  merchantId: String,
  email: String,
  publicName: Option[String]
)

object AccountInfo {

  import play.api.libs.json._

  implicit val config = JsonConfiguration(JsonNaming.SnakeCase)  

  implicit val fmt = Json.reads[AccountInfo]

}