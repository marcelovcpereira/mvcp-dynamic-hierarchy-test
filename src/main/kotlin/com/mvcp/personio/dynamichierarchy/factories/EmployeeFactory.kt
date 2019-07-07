package com.mvcp.personio.dynamichierarchy.factories

import com.google.gson.Gson
import com.mvcp.personio.dynamichierarchy.entities.Employee
import com.mvcp.personio.dynamichierarchy.exceptions.InvalidInputException
import com.mvcp.personio.dynamichierarchy.validators.InputValidator
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class EmployeeFactory(
        @Autowired
        val validator: InputValidator
) {
    fun buildFromJson(json: String): List<Employee> {
        var list = ArrayList<Employee>()
        try {
            var gson = Gson().fromJson(json, Map::class.java)
            gson.forEach { employee, supervisor ->
                var supervisorObj = Employee(supervisor as String)
                var employeeObj = Employee(employee as String, supervisorObj)

                list.add(supervisorObj)
                list.add(employeeObj)
            }
            validator.validate(list)
            return list
        } catch (e: Exception) {
            throw InvalidInputException("Invalid json input: ${e.message}")
        }
    }
}