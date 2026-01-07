package com.example.coroutinesapp.ui.oop.abstraction


abstract class Employee(val id: Int, val name: String) {
    abstract fun calculateSalary(): Double
}

class SalariedEmployee(id: Int, name: String, val salary: Double) : Employee(id, name) {
    override fun calculateSalary(): Double {
        return salary
    }
}
class HourlyEmployee(id: Int, name: String, val hourlyPay: Double, val hoursWorked: Double) : Employee(id, name){
    override fun calculateSalary(): Double {
        return hourlyPay * hoursWorked
    }
}
class TraineeEmployee(id: Int, name: String) : Employee(id, name) {
    override fun calculateSalary(): Double {
        return 1000.0
    }
}

fun getEmployeeInfo(employee: Employee): String{
    return "${employee.id} ${employee.name} ${employee.calculateSalary()} \n"
}
