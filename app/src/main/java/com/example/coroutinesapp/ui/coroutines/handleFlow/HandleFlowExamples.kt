package com.example.coroutinesapp.ui.coroutines.handleFlow

import android.util.Log
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.runBlocking

class HandleFlowExamples {
    fun test() {
        runBlocking {
            getDataFlow().collect { data ->
                Log.d(TAG, "received: $data")
            }

            getDataFlow2().collect { data ->
                Log.d(TAG, "received: $data")
            }

            countdownTimer().collect { data ->
                Log.d(TAG, "timer: $data")
            }
        }
    }

    fun getDataFlow(): Flow<String> {
        return flow {
            emit("Strat loading")
            delay(1000)

            emit("get Data")
            delay(1000)

            emit("start filtering data")
            delay(1000)

            emit("data is ready")
            delay(1000)

            emit("end loading")
        }
    }

    fun getDataFlow2(): Flow<String> = flow {
        emit("Data 1")
        emit("Data 2")
        emit("Data 3")
    }


    private val TAG = "HandleFlowExamples"
    var startTime = 10
    fun countdownTimer(): Flow<Int> = flow {
        while (startTime >= 0) {
            emit(startTime)
            delay(1000)
            startTime--
        }
    }

    var counter2 = 0
    fun getData2(): Flow<String> = flow {
        emit("this is $counter2")
        delay(1000)
        counter2++

        emit("that is $counter2")
        delay(1000)
        counter2++

        emit("this is $counter2")
        delay(1000)
        counter2++
        emit("that is $counter2")
        delay(1000)
        counter2++

    }

    fun getData3(): Flow<Int> = flow {
        repeat(5) { counter ->
            Log.d(TAG, "emitting: $counter")
        }
    }

    fun test2() {
        runBlocking {
            getData2()
                .filter {
                    it.startsWith("this")
                }
                .collect {
                    Log.d(TAG, "received: $it")
                }

            getData3().collect {
                Log.d(TAG, "received: $it")
            }
        }
    }


    val results = listOf("results for kotlin", "results for java", "results for swift")
    fun searchFlow(): Flow<String> = flow {
        emit(results[0])
        delay(100)
        emit(results[1])
        delay(100)
        emit(results[2])
        delay(100)
    }

    fun test_getLatestResult() {
        runBlocking {
            searchFlow()
                .onEach {
                    Log.d(TAG, "we got: $it")
                }
                .collectLatest {
//                    delay(1000)
                    Log.d(TAG, "result is: $it")
                }
        }

    }


}