package com.typedlabs.coinpayments4s

import com.typedlabs.coinpayments4s.models._
import com.roundeights.hasher.Algo
import com.roundeights.hasher.Implicits._
import play.api.libs.json.{Json, Reads}
import scalaj.http._
import scala.language.postfixOps

class CoinPaymentsClient(publicKey: String, secretKey: String) {

  private val baseUrl = "https://www.coinpayments.net/api.php"
  
  def getBasicAccountInfo(): Either[CoinPaymentsHttpError, AccountInfo] = {
    api[AccountInfo]("cmd" -> "get_basic_info")
  }

  def getSupportedCoins(): Either[CoinPaymentsHttpError, Map[String,Coin]] = {
    api[Map[String,Coin]]("cmd" -> "rates")
  }

  def createTransaction(transactionRequest: TransactionRequest): Either[CoinPaymentsHttpError, TransactionResponse] = {
    api[TransactionResponse](
      "cmd" -> "create_transaction",
      "amount" -> transactionRequest.amount.toString,
      "buyer_email" -> transactionRequest.buyerEmail,
      "currency1" -> transactionRequest.currency1,
      "currency2" -> transactionRequest.currency2
    )
  }    

  private def api[A](params: (String, String)*)(implicit fmt: Reads[A]): Either[CoinPaymentsHttpError, A] = {
    
    val sha512Hmac: Algo= Algo.hmac(secretKey).sha512

    // Append default arguments to Post params
    val combinedParams = params ++ Seq("key" -> publicKey, "version" -> "1")
    
    // combine and create the params string to be signed
    val keyBaseString = 
      combinedParams.map{
        case (key, value) => s"${key}=${value}"
      }.mkString("&")

    // sign string with java hmac512 
    val keySignature = sha512Hmac(keyBaseString)
    
    // create request
    val req = Http(s"${baseUrl}")   
      .timeout(connTimeoutMs = 5000, readTimeoutMs = 10000)   
      .postForm(combinedParams)
      .header("HMAC", keySignature)
      .header("User-Agent", "newlife-http/1.0")

    // get response
    val resp = req.asString

    // parse json response
    val jsonBody = Json.parse(resp.body)

    // check if response has error if not parse json into object
    if( (jsonBody \ "error").as[String] == "ok"){
      val r = (jsonBody \ "result").as[A]
      Right(r)
    } else {  
      // return wrapped error
      Left(jsonBody.as[CoinPaymentsHttpError])
    }      

  }

}