package com.example.coroutinesapp.ui.coroutines.examples

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class MediumArticleExamples {

    //    suspend fun fetchUser(id: String): User = suspendCancellableCoroutine { continuation ->
//     api.getUser(id, object : Callback<User> {
//        override fun onResponse(call: Call<User>, response: Response<User>) {
//            continuation.resume(response.body())
//        }
//
//        override fun onFailure(call: Call<User>, t: Throwable) {
//            continuation.resumeWithException(t)
//        }
//    })
//    }
    private fun testFlow() {
        runBlocking {
            launch {
                for (k in 1..5) {
                    Log.d("TAG1", "I'm not blocked $k")
                    delay(1000L)
                }
            }
            numbers().collect { value -> println(value) }

        }
    }

    private fun numbers(): Flow<Int> = flow {
        for (i in 1..5) {
            delay(1000L)
            emit(i)
            Log.d("TAG1", "numbers emit $i")
        }
    }

    private fun jobHierarchy() {
        runBlocking {
            val parentJob = launch {
                val childJob = launch {
                    while (true) {
                        Log.d("TAG1", "Child is running")
                        delay(500L)
                    }
                }
                delay(2000L)
                Log.d("TAG1", "Cancelling child job")
                childJob.cancel()
            }
            parentJob.join()
        }
    }

    private fun structuredConcurrency() {
        runBlocking {
            Log.d("TAG1_1", "Start of runBlocking")
            launch {
                delay(1000L)
                Log.d("TAG1_2", "Task from runBlocking")
            }
            coroutineScope {
                launch {
                    delay(2000L)
                    Log.d("TAG1_3", "Task from nested launch")
                }
                delay(500L)
                Log.d("TAG1_4", "Task from coroutine scope")
            }
            Log.d("TAG1_5", "Coroutine scope is over")
        }


        /**
         * The output of the above code will be:
         *
         * 12:49:52.523 TAG1_1   Start of runBlocking
         * 12:49:53.029 TAG1_4   Task from coroutine scope  , coroutine 2 after 500L
         * 12:49:53.530 TAG1_2   Task from runBlocking      , coroutine 1 after 1000L
         * 12:49:54.530 TAG1_3   Task from nested launch    , coroutine inside coroutine 2 after 2000L
         * 12:49:54.531 TAG1_5   Coroutine scope is over
         *
         *
         * from above output we get the following:
         * in normal code the output will be:
         * TAG1_1
         * TAG1_2
         * TAG1_3
         * TAG1_4
         * TAG1_5
         *
         * but in coroutines the output will be:
         * TAG1_1
         * TAG1_4
         * TAG1_2
         * TAG1_3
         * TAG1_5
         *
         * In this code, runBlocking creates a new coroutine scope, and within that
         * scope, we launch a new coroutine and create a new coroutineScope. The
         * coroutineScope blocks the current coroutine until all of its child coroutines
         * are completed. So, the message "Coroutine scope is over" is printed only
         * after the nested launch completes its execution.
         *
         * --------------------
         *
         * runBlocking have 2 child coroutines
         * if we remove the coroutineScope then the output will be:
         * TAG1_1 -> TAG1_2 -> TAG1_5,
         *
         * if we add the coroutineScope then the output will be:
         *
         * TAG1_1 : start of coroutine
         * TAG1_4 : from coroutineScope
         * TAG1_2 : from Launch outSide coroutineScope
         * TAG1_3 : from coroutineScope again
         * TAG1_5 : after all child coroutines (coroutineScope(task1,task2) and launch) are completed
         *
         * */
    }

    private fun testLaunchCoroutine() {
        /**
        Launch coroutine is used to Fire and forget we don 't care about the result
         */
        Log.d("TAG1", " 000 ")
        GlobalScope.launch {
            Log.d("TAG1", " 001 ")
            delay(1000L)
            Log.d("TAG1", " 002 ")
        }
        Log.d("TAG1", " 003 ")
        Thread.sleep(4000L)
        Log.d("TAG1", " 004 ")
    }

    private fun testAsyncCoroutine() {
        /**
        Async coroutine is used to get the result (return Deferred<T>)
         */
        Log.d("TAG1", " 000 ")
        GlobalScope.launch {
            Log.d("TAG1", " 001 ")
            val result = async {
                computeResult()
            }
            Log.d("TAG1", " 002  ${result.await()} ")
        }
        Thread.sleep(4000L)
        Log.d("TAG1", " 003 ")
    }

    private suspend fun computeResult(): Int {
        delay(6000L)
        return 42
    }

    private fun testRunBlockingCoroutine() {
        /**
        RunBlocking is used to connect between non-coroutine code and coroutine code
        RunBlocking is the entire point to run coroutines from the main thread
        RunBlocking Allows calling suspend functions from regular code.
         */
        Log.d("TAG1", " 000 ")
        runBlocking {
            launch {
                delay(1000L)
                Log.d("TAG1", " 001 Hello from Coroutine! ")
            }
            Log.d("TAG1", " 002 Hello from Main Thread! ")
        }
        Log.d("TAG1", " 003 ")
    }

    private fun dispatchers() {
        /**
        Coroutine Context and dispatchers
        Context is like rules and tools for coroutines
        Dispatchers are the tools that decide which thread the coroutine will run on
        Dispatchers.IO is used for network and database operations
        Dispatchers.Default is used for CPU intensive operations
        ,like sorting a list and parsing a JSON and run Complex algorithms
        Dispatchers.Main is used for UI operations
        Dispatchers.Unconfined is used for not specifying a dispatcher
         */

        runBlocking {
            launch(Dispatchers.IO) {
                println("IO: ${Thread.currentThread().name}")
            }
            launch(Dispatchers.Default) {
                println("Default: ${Thread.currentThread().name}")
            }
            launch(Dispatchers.Main) {
                println("Main: ${Thread.currentThread().name}")
            }
        }

    }

}