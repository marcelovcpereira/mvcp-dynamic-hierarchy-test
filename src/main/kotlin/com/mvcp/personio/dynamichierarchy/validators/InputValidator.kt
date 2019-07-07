package com.mvcp.personio.dynamichierarchy.validators

import com.mvcp.personio.dynamichierarchy.entities.Employee
import org.springframework.stereotype.Component

@Component
class InputValidator {
    fun validate(array : List<Employee>) : Boolean {
        return true
    }
}
