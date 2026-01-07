package com.example.coroutinesapp.ui.oop.abstraction

abstract class Payment {
    fun process(amount: Double) {
        validate(amount)
        executePayment(amount)
    }
    fun getReceipt(): String{
        return generateReceipt()
    }
    protected abstract fun executePayment(amount: Double)
    protected open fun validate(amount: Double) {
        if (amount <= 0) {
            throw IllegalArgumentException("Invalid amount")
        }
    }

    protected open fun sendReceipt() {
        println("Receipt sent")
    }
    protected abstract fun generateReceipt(): String
}

class CardPayment(private val cardNumber: String) : Payment() {
    override fun executePayment(amount: Double) {
        println("Charging $amount to card $cardNumber")
    }

    override fun generateReceipt(): String {
        return "Receipt for card $cardNumber"
    }
}

class WalletPayment(private val walletId: String) : Payment() {
    override fun validate(amount: Double) {
        super.validate(amount)
        println("Wallet balance checked")
    }

    override fun executePayment(amount: Double) {
        println("Paying $amount from wallet $walletId")
    }
    override fun generateReceipt(): String {
        return "Receipt for wallet $walletId"
    }
}

class PosPayment(private val terminalId: String) : Payment() {
    override fun executePayment(amount: Double) {
        println("Processing POS payment on terminal $terminalId")
    }
    override fun generateReceipt(): String {
        return "Receipt for POS on terminal $terminalId"
    }
}

class PaymentService(private val payment: Payment) {
    fun pay(amount: Double) {
        payment.process(amount)
    }
    fun receipt(): String{
        return payment.getReceipt()
    }
}