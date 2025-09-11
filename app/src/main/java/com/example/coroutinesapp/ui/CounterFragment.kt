package com.example.coroutinesapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.coroutinesapp.databinding.FragmentCounterBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CounterFragment : Fragment() {
    private lateinit var binding: FragmentCounterBinding
    private var counterJob: Job? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentCounterBinding.inflate(inflater, container, false)
        val number = 1000
        val delay = 10
        binding.resetBtn.setOnClickListener { startCounting(number, delay) }
        binding.stopBtn.setOnClickListener { stopCounting() }
        return binding.root
    }

    private fun startCounting(number: Int, delay: Int) {
        // Stop any existing counting
        stopCounting()

        counterJob = CoroutineScope(Dispatchers.IO).launch {
            withContext(Dispatchers.Main){
                handleUI(number, delay)
            }
        }
    }

    suspend fun handleUI(number: Int, delay: Int) {
        for (i in 1..number) {
            binding.counterTextview.text = i.toString()
            binding.progressBar1.max = number
            binding.progressBar1.progress = i
            delay(delay.toLong())
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        stopCounting()
    }

    private fun stopCounting() {
        counterJob?.cancel()
        counterJob = null
        binding.counterTextview.text = 0.toString()
        binding.progressBar1.progress = 0
    }


}