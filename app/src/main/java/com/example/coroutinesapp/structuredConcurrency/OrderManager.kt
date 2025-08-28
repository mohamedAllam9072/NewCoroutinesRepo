package com.example.coroutinesapp.structuredConcurrency

import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class OrderManager {
    // Custom scope for order processing
    private val orderScope = CoroutineScope(
        Dispatchers.IO +
                Job() +
                CoroutineName("OrderProcessing")
    )

    fun processOrder(order: Order) {
        orderScope.launch {
            // Order processing logic
            validateOrder(order)
            processPayment(order)
            prepareOrder(order)
            deliverOrder(order)
        }
    }

    fun cleanup() {
        orderScope.cancel() // Cancel all order processing on cleanup
    }

    data class Order(val id: String)

    fun validateOrder(order: Order) {
        println("Validating order ${order.id}")
    }

    fun processPayment(order: Order) {
        println("Processing payment for order ${order.id}")
    }

    fun prepareOrder(order: Order) {
        println("Preparing order ${order.id}")
    }

    fun deliverOrder(order: Order) {
        println("Delivering order ${order.id}")
    }
}