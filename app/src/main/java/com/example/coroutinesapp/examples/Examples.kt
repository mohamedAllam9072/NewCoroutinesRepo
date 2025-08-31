package com.example.coroutinesapp.examples

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

class Examples {

    fun handleParallelCoroutines() {
        GlobalScope.launch {
            val result1 = callApi1()
            val result2 = callApi2()
            println("Result 1: $result1")
            println("Result 2: $result2")
        }
        // if call two suspend functions in parallel ,every function did not block the main thread
        // because of every function is a coroutine and they are running in parallel
        // every coroutine have its own dispatcher and every dispatcher have its own thread
    }

    suspend fun callApi1(): String {
        delay(3000L)
        println("callApi1 current thread name: ${Thread.currentThread().name}")
        return "callApi1 response"
    }

    suspend fun callApi2(): String {
        delay(3000L)
        println("callApi2 current thread name: ${Thread.currentThread().name}")
        return "callApi2 response"
    }


    fun handleContextSwitching() {
        GlobalScope.launch(Dispatchers.IO) {
            println("handleContextSwitching current thread name: ${Thread.currentThread().name}")
            val result = callApi1()
            withContext(Dispatchers.Main) { handleUI(result) }
        }
    }

    fun handleUI(text: String) {
        println("handleUI current thread name: ${Thread.currentThread().name} ,text $text")
    }

    fun handleSequentialCoroutines() {
        GlobalScope.launch {
            println("handleSequentialCoroutines current thread name: ${Thread.currentThread().name}")
            val result = async { callApi1() }
            if (result.await().isNotEmpty() && result.await() == "callApi1 response") {
                callApi2()
                println("handleSequentialCoroutines success")
            } else {
                println("handleSequentialCoroutines failed")
            }
        }
    }

    fun diff_runBlocking_coroutineScope(key:Boolean) {
        println("differance_between_runBlocking_and_coroutineScope")
        if (key){
            handleUI("text1")
            GlobalScope.launch(Dispatchers.Main) {
                println("GlobalScope start")
                delay(3000L)
                println("GlobalScope end")
            }
            handleUI("text2")
        }else{
            handleUI("text1")
            runBlocking {
                println("runBlocking start")
                delay(3000L)
                println("runBlocking end")
            }
            handleUI("text2")
        }

        /**
         * the result of the above code will be:
         * if key = true => call GlobalScope

         text1
         text2
         GlobalScope start
         GlobalScope end

         because of GlobalScope not block the main thread


         if key = false => call runBlocking

         text1
         runBlocking start and wait for 3 seconds
         runBlocking end
         text2

         because of runBlocking block the main thread

         * */

    }


    fun handleCoroutineContexts(){
        // Dispatches.Main  handle UI operations
        // Dispatchers.IO  handle network and offline database operations and file operations
        // Dispatchers.Default  handle CPU intensive operations like sorting a list and parsing a JSON
        // Dispatchers.Unconfined  not specifying a dispatcher
        GlobalScope.launch() {
            println("1 - (): ${Thread.currentThread().name}")
        }
        GlobalScope.launch(Dispatchers.IO) {
            println("2 - IO: ${Thread.currentThread().name}")
        }
        GlobalScope.launch(Dispatchers.Default) {
            println("3 - Default: ${Thread.currentThread().name}")
        }
        GlobalScope.launch(Dispatchers.Main) {
            println("4 - Main: ${Thread.currentThread().name}")
        }
        GlobalScope.launch(Dispatchers.Unconfined) {
            println("5 - Unconfined: ${Thread.currentThread().name}")
        }
        GlobalScope.launch(newSingleThreadContext("MyThread")) {
            println("6 - MyThread: ${Thread.currentThread().name}")
        }
        GlobalScope.launch(Dispatchers.IO) {
            println("7 - IO: ${Thread.currentThread().name}")
            withContext(Dispatchers.Main) {
                println("8 - withContext: ${Thread.currentThread().name}")
            }
        }



    }
}