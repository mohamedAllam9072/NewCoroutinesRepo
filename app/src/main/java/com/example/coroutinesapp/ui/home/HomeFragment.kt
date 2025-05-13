package com.example.coroutinesapp.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.coroutinesapp.databinding.FragmentHomeBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        testRunBlockingCoroutine()
        return root
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
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}