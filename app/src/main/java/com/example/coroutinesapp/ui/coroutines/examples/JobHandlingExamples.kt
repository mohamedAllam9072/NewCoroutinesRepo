package com.example.coroutinesapp.ui.coroutines.examples

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withTimeout

class JobHandlingExamples {
    /**
     * handleJob()
     *
     * result:
     *
     * 14:56:00.702 System.out              I  Job: I'm sleeping 0 ...
     * 14:56:01.705 System.out              I  Job: I'm sleeping 1 ...
     * 14:56:02.709 System.out              I  Job: I'm sleeping 2 ...
     * 14:56:03.713 System.out              I  Job: I'm sleeping 3 ...
     * 14:56:04.717 System.out              I  Job: I'm sleeping 4 ...
     * 14:56:05.722 System.out              I  Main thread , Job: Now I can quit.
     *
     * comments:
     *
     * if handleJob(true) to use job.join()
     *
     *
     * we run in Dispatchers.Default coroutineScope and repeat 5 times
     * then we use job.join() to wait until job is done then attach to main thread
     *
     *==================================================================
     * if handleJob(false)  to use job.cancel()
     *
     * result:
     * 14:58:57.597 System.out              I  Job: I'm sleeping 0 ...
     * 14:58:58.600 System.out              I  Job: I'm sleeping 1 ...
     * 14:58:59.199 System.out              I  Main: I'm tired of waiting!
     * 14:58:59.202 System.out              I  Main: Now I can quit.
     *
     * comments:
     *
     * after delay(1600L) we use job.cancel() to cancel the job
     * run iteration 0 and after 1 second,
     * then run iteration 1 and after 2 second,
     * then coroutine canceled and after 2 second , because of job.cancel() after delays(2000L)
     *
     * */
    fun handleJob(key: Boolean) {
        val job = GlobalScope.launch(Dispatchers.Default) {
            repeat(5) {
                println("Job: I'm sleeping $it ...")
                delay(1000L)
            }
        }

        runBlocking {
            if (key) {
                job.join() // wait until job is done
                println("Main thread , Job: Now I can quit.")
            } else {
                delay(1600L)
                println("Main: I'm tired of waiting!")
                job.cancel() // cancels the job
                println("Main: Now I can quit.")
            }
        }
    }

    /**
     * cancelJob()
     *
     * result:
     *
     * 14:47:23.787 System.out              I  coroutine is still working 0 ...
     * 14:47:24.790 System.out              I  coroutine is still working 1 ...
     * 14:47:25.792 System.out              I  Main thread is continuing ...
     *
     * comments:
     *
     * coroutine
     * run iteration 0 and after 1 second,
     * then run iteration 1 and after 2 second,
     * then coroutine canceled and after 2 second , because of job.cancel() after delays(2000L)
     *
     * THAT IS WHAT WE NEED
     * */
    fun cancelJob() {
        val job = GlobalScope.launch(Dispatchers.Default) {
            repeat(5) {
                println("coroutine is still working $it ...")
                delay(1000L)
            }
        }
        runBlocking {
            delay(2000L)
            job.cancel() // cancels the job
            println("Main thread is continuing ...")
        }
    }

    /**
     * handleComplexTaskCancellation()
     *
     * result:
     *
     * 15:11:54.992 System.out              I  start handle Complex Task Cancellation
     * 15:11:55.043 System.out              I  result of 30 = 832040
     * 15:11:55.106 System.out              I  result of 31 = 1346269
     * 15:11:55.188 System.out              I  result of 32 = 2178309
     * 15:11:55.306 System.out              I  result of 33 = 3524578
     * 15:11:55.499 System.out              I  result of 34 = 5702887
     * 15:11:55.813 System.out              I  result of 35 = 9227465
     * 15:11:56.318 System.out              I  result of 36 = 14930352
     * 15:11:56.996 System.out              I  job canceled after 2 seconds ,Now we are in the main thread
     * 15:11:57.130 System.out              I  result of 37 = 24157817
     * 15:11:58.444 System.out              I  result of 38 = 39088169
     * 15:12:00.576 System.out              I  result of 39 = 63245986
     * 15:12:04.026 System.out              I  result of 40 = 102334155
     * 15:12:04.026 System.out              I  end handle Complex Task Cancellation
     *
     * comments:
     *  we need to cancel job after 2 seconds to stop the coroutine,
     *  but the coroutine is still working,
     *
     *  reason is because of complex task the the coroutine is still working,and do not check if the job is active, or canceled
     *  so to handle it we will use isActive to check if the job is active or canceled
     * */
    fun handleComplexTaskCancellation() {
        val job = GlobalScope.launch(Dispatchers.Default) {
            println("start handle Complex Task Cancellation")
            for (i in 30..40) {
                println("result of $i = ${fib(i)}")
            }
            println("end handle Complex Task Cancellation")
        }

        runBlocking {
            delay(2000L)
            job.cancel() // cancels the job
            println("job canceled after 2 seconds ,Now we are in the main thread")
        }
    }

    /**
     * handleComplexTaskCancellation_check_isActive()
     *
     * result:
     *
     * 15:18:37.083 System.out              I  start handle Complex Task Cancellation
     * 15:18:37.192 System.out              I  result of 30 = 832040
     * 15:18:37.267 System.out              I  result of 31 = 1346269
     * 15:18:37.381 System.out              I  result of 32 = 2178309
     * 15:18:37.535 System.out              I  result of 33 = 3524578
     * 15:18:37.728 System.out              I  result of 34 = 5702887
     * 15:18:38.042 System.out              I  result of 35 = 9227465
     * 15:18:38.546 System.out              I  result of 36 = 14930352
     * 15:18:39.090 System.out              I  job canceled after 2 seconds ,Now we are in the main thread
     * 15:18:39.357 System.out              I  result of 37 = 24157817
     * 15:18:39.357 System.out              I  end handle Complex Task Cancellation
     *
     * comments:
     * after delay(2000L) we use job.cancel() to cancel the job and check if the job is active or canceled
     * */
    fun handleComplexTaskCancellation_check_isActive() {
        val job = GlobalScope.launch(Dispatchers.Default) {
            println("start handle Complex Task Cancellation")
            for (i in 30..40) {
                if (isActive) {
                    println("result of $i = ${fib(i)}")
                }
            }
            println("end handle Complex Task Cancellation")
        }

        runBlocking {
            delay(2000L)
            job.cancel() // cancels the job
            println("job canceled after 2 seconds ,Now we are in the main thread")
        }
    }

    /**
     * handleComplexTaskCancellation_withTimeout()
     *
     * result:
     *
     * 15:27:44.940 System.out              I  start handle Complex Task Cancellation
     * 15:27:44.982 System.out              I  result of 30 = 832040
     * 15:27:45.027 System.out              I  result of 31 = 1346269
     * 15:27:45.102 System.out              I  result of 32 = 2178309
     * 15:27:45.223 System.out              I  result of 33 = 3524578
     * 15:27:45.420 System.out              I  result of 34 = 5702887
     * 15:27:45.746 System.out              I  result of 35 = 9227465
     * 15:27:46.258 System.out              I  result of 36 = 14930352
     * 15:27:47.072 System.out              I  result of 37 = 24157817
     * 15:27:47.073 System.out              I  end handle Complex Task Cancellation
     *
     * comments:
     * with timeout we can cancel the job after a specific time and check if the job is active or canceled
     * and we do not need to use job.cancel() to cancel the job
     * */
    fun handleComplexTaskCancellation_withTimeout() {
        val job = GlobalScope.launch(Dispatchers.Default) {
            println("start handle Complex Task Cancellation")
            withTimeout(2000L) {
                for (i in 30..40) {
                    if (isActive) {
                        println("result of $i = ${fib(i)}")
                    }
                }
            }
            println("end handle Complex Task Cancellation")
        }
    }

    fun fib(n: Int): Long {
        return when (n) {
            0 -> 0
            1 -> 1
            else -> fib(n - 1) + fib(n - 2)
        }
    }


}


