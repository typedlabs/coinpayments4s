# coinpayments4s

coinpayments4s is a http client for coinpayments.net API

This is a work in progress which supports three **cmd**s.

* get_basic_info
* rates
* create_transaction

The client is based on a simple class for now with a private polymorphic method **api** method.

### Example

~~~scala
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
~~~

### Tests
To run the tests you need to export **COIN_PAYMENTS_TEST_PUBLIC_KEY** and **COIN_PAYMENTS_TEST_PRIVATE_KEY** environments variables.
