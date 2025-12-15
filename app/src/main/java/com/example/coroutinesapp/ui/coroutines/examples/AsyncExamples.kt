package com.example.coroutinesapp.ui.coroutines.examples

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.system.measureTimeMillis

class AsyncExamples {

    fun handleParallelCoroutines() {
        sequentialCalling()  // time is 6000 millSeconds (3000+3000)
        parallelCallingWithLaunch() // time is 3000 millSeconds (3000 and 3000 in parallel)
        parallelCallingWithAsync()  // time is 3000 millSeconds (3000 and 3000 in parallel)  // async is faster than launch and cleaner
    }
    fun sequentialCalling(){
        GlobalScope.launch(Dispatchers.IO) {
            val time_1 = measureTimeMillis {
                val result1 = callApi1()
                val result2 = callApi2()
                println("callApi1 : $result1")
                println("callApi2 : $result2")
            }
            println("time_1 : $time_1")
        }
    }
    fun parallelCallingWithLaunch(){
        GlobalScope.launch(Dispatchers.IO) {
            val time_2 = measureTimeMillis {
                var result1: String? = null
                var result2: String? = null
                val job1 = launch { result1 = callApi1() }
                val job2 = launch { result2 = callApi2() }
                job1.join()
                job2.join()
                println("callApi1 : $result1")
                println("callApi2 : $result2")
            }
            println("time_2 : $time_2")
        }
    }
    fun parallelCallingWithAsync(){
        GlobalScope.launch(Dispatchers.IO) {
            val time_3 = measureTimeMillis {
                val result1 = async { callApi1() }
                val result2 = async { callApi2() }
                println("callApi1 : ${result1.await()}")
                println("callApi2 : ${result2.await()}")
            }
            println("time_3 : $time_3")
        }
    }
}



suspend fun callApi1(): String {
    delay(3000L)
    return "callApi_1_Response"
}

suspend fun callApi2(): String {
    delay(3000L)
    return "callApi_2_Response"
}