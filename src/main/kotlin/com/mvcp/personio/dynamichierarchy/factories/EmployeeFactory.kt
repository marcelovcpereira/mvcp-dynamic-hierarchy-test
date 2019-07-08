package com.mvcp.personio.dynamichierarchy.factories

import com.google.gson.Gson
import com.mvcp.personio.dynamichierarchy.entities.Employee
import com.mvcp.personio.dynamichierarchy.exceptions.InvalidInputException
import com.mvcp.personio.dynamichierarchy.validators.InputValidator
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
/**
 * Component responsible for creating Employee's objects based on input.
 * The factory contains a validator component for assuring only valid Employees are created
 *
 * @author      Marcelo Pereira
 * @version     1.0.0
 * @since       2019-06-08
 */
@Component
class EmployeeFactory() {
    /**
     * Returns a list of Employees from a JSON input.
     *
     * @param json Input string to be processed into a list of Employees
     */
    fun buildFromJson(json: String): List<Employee> {
        println("Building from json: $json")
        var list = ArrayList<Employee>()
        try {
            var gson = Gson().fromJson(json, Map::class.java)
            gson.forEach { employee, supervisor ->
                var supervisorObj = Employee(supervisor as String)
                var employeeObj = Employee(employee as String, supervisorObj)

                list.add(supervisorObj)
                list.add(employeeObj)
            }
            return list
        } catch (e: Exception) {
            throw InvalidInputException("Invalid json input: ${e.message}")
        }
    }
}