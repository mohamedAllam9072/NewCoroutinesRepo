package com.example.coroutinesapp.ui.oop

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.coroutinesapp.databinding.FragmentOopNotesBinding
import com.example.coroutinesapp.ui.oop.abstraction.HourlyEmployee
import com.example.coroutinesapp.ui.oop.abstraction.Payment
import com.example.coroutinesapp.ui.oop.abstraction.PaymentService
import com.example.coroutinesapp.ui.oop.abstraction.SalariedEmployee
import com.example.coroutinesapp.ui.oop.abstraction.TraineeEmployee
import com.example.coroutinesapp.ui.oop.abstraction.WalletPayment
import com.example.coroutinesapp.ui.oop.abstraction.getEmployeeInfo

class OOPNotesFragment : Fragment() {
    private lateinit var binding: FragmentOopNotesBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentOopNotesBinding.inflate(inflater, container, false)

        binding.abstractTest.setOnClickListener {
            testAbstractionExample2()
        }
        return binding.root
    }

    fun testAbstractionExample2() {
        val payment: Payment = WalletPayment("WALLET-001")
        val service = PaymentService(payment)
        service.pay(500.0)
        print(service.receipt())
    }

    fun testAbstractionExample() {
        val salariedEmployee = SalariedEmployee(1, "ahmed ahmed", 2000.0)
        val hourlyEmployee = HourlyEmployee(2, "mahmoud mahmoud", 100.0, 160.0)
        val traineeEmployee = TraineeEmployee(3, "mohamed mohamed")

        print(getEmployeeInfo(salariedEmployee))
        print(getEmployeeInfo(hourlyEmployee))
        print(getEmployeeInfo(traineeEmployee))
    }

    fun print(text: String) {
        binding.resultArea.append(text)
    }
}