package com.example.coroutinesapp.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation.findNavController
import com.example.coroutinesapp.R
import com.example.coroutinesapp.databinding.FragmentHomeBinding
import com.example.coroutinesapp.examples.AsyncExamples
import com.example.coroutinesapp.examples.Examples
import com.example.coroutinesapp.examples.JobHandlingExamples
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        binding.button1.setOnClickListener { Examples().handleParallelCoroutines() }
        binding.button2.setOnClickListener { Examples().handleContextSwitching() }
        binding.button3.setOnClickListener { Examples().handleSequentialCoroutines() }
        binding.button4.setOnClickListener { Examples().diff_runBlocking_coroutineScope(true) }
        binding.button5.setOnClickListener { Examples().diff_runBlocking_coroutineScope(false) }
        binding.button8.setOnClickListener { Examples().handleCoroutineContexts() }
        binding.buttonCounter.setOnClickListener { findNavController(it).navigate(R.id.action_navigation_home_to_counterFragment) }
        binding.button6.setOnClickListener { JobHandlingExamples().handleJob(true) }
        binding.button7.setOnClickListener { JobHandlingExamples().handleJob(false) }
        binding.cancelJob.setOnClickListener { JobHandlingExamples().cancelJob() }
        binding.handleComplexTaskCancellation.setOnClickListener { JobHandlingExamples().handleComplexTaskCancellation() }
        binding.handleComplexTaskCancellationCheckIsActive.setOnClickListener { JobHandlingExamples().handleComplexTaskCancellation_check_isActive() }
        binding.handleComplexTaskCancellationWithTimeout.setOnClickListener { JobHandlingExamples().handleComplexTaskCancellation_withTimeout() }
        binding.handleParallelCoroutines.setOnClickListener { AsyncExamples().handleParallelCoroutines() }
        return binding.root
    }

}