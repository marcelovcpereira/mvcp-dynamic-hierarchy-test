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
        println("[DEBUG] building...")
        try {
            var gson = Gson().fromJson(json, Map::class.java)
            println("[DEBUG] object built: $gson")
            gson.forEach { key, value ->
                println("[DEBUG] key: $key / value: $value")
                var keyObject = Employee(key as String)
                var valueObject = Employee(value as String, keyObject)
                list.add(keyObject)
                list.add(valueObject)
            }
            println("[DEBUG] validating $list")
            validator.validate(list)
            return list
        } catch (e: Exception) {
            throw InvalidInputException("Invalid json input: ${e.message}")
        }
    }
}