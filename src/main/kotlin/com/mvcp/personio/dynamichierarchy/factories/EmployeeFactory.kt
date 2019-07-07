package com.mvcp.personio.dynamichierarchy.factories

import com.mvcp.personio.dynamichierarchy.entities.Employee
import org.springframework.stereotype.Component

@Component
class EmployeeFactory {
    companion object {
        fun buildFromJson(json: String) : Employee {
            println("[DEBUG] building $json")
            return Employee(json)
        }
    }
}

