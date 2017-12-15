package com.typedlabs.coinpayments4s

import com.typesafe.config.ConfigFactory
import com.typedlabs.coinpayments4s.models._
import org.scalatest._

class CoinPaymentsClientSpec extends FlatSpec with Matchers with EitherValues {

  val conf = ConfigFactory.load("application.test.conf").getConfig("test")
  val publicKey = conf.getString("coinpayments4s.publicKey")
  val privateKey = conf.getString("coinpayments4s.privateKey")

  val coinPaymentsClient = new CoinPaymentsClient(publicKey, privateKey)

  "coinPaymentsClient" should "get account info" in {  
    val accountInfo = coinPaymentsClient.getBasicAccountInfo()    
    (accountInfo === 'right)
  }

 "coinPaymentsClient" should "get supported coins" in {  
    val supportedCoins = coinPaymentsClient.getSupportedCoins()    
    (supportedCoins === 'right)
  }

  "coinPaymentsClient" should "create a transaction" in {  
    val transactionRequest = TransactionRequest(10.0, "joao@typedlabs.com")
    val transactionResponse = coinPaymentsClient.createTransaction(transactionRequest)
    transactionResponse should be ('right)
  }

}