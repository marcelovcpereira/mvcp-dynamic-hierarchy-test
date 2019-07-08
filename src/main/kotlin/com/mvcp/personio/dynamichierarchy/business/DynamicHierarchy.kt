package com.mvcp.personio.dynamichierarchy.business

import com.mvcp.personio.dynamichierarchy.entities.Employee
import com.mvcp.personio.dynamichierarchy.exceptions.EmployeeNotFoundException
import com.mvcp.personio.dynamichierarchy.exceptions.InvalidInputException
import com.mvcp.personio.dynamichierarchy.factories.EmployeeFactory
import com.mvcp.personio.dynamichierarchy.managers.EmployeeManager
import com.mvcp.personio.dynamichierarchy.validators.InputValidator
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
/**
 * The Dynamic Hierarchy is the heart of the application and is responsible for Creating a Hierarchy, Exhibiting a
 * Hierarchy and for finding an Employee's Supervisors inside a Hierarchy
 *
 * @author      Marcelo Pereira
 * @version     1.0.0
 * @since       2019-06-07
 */
@Component
class DynamicHierarchy(
        /**
         * Persistence manager for Employees
         */
        @Autowired
        val employees: EmployeeManager,

        /**
         * Factory manager for Employess
         */
        @Autowired
        val empFactory: EmployeeFactory,

        /**
         * Validator for Employees
         */
        @Autowired
        val validator: InputValidator
) {
    /**
     * Returns the Supervisor of an Employee together with it's Supervisor's Supervisor
     *
     * @param name Name of the Employee being checked
     */
    fun getSupervisors(name: String): String {
        var employee= employees.getByName(name)
        if (employee == null) {
            throw EmployeeNotFoundException("Employee with name $name not found")
        }

        var manager = employees.getManagerByName(name)
        var managerName = "<null>"
        if (manager != null) {
            managerName = manager.name
        }

        var superManager = employees.getManagerByName(managerName)
        var superManagerName = "<null>"
        if (superManager != null) {
            superManagerName = superManager.name
        }
        return "manager: $managerName, supermanager: $superManagerName"
    }

    /**
     * Persists a Hierarchy after validation and returns a Pretty JSON-tree version of it.
     *
     * @param body Input JSON string to be processed
     */
    fun putHierarchy(body: String): String {
        try {
            clean()
            var list = empFactory.buildFromJson(body)
            validator.validate(list)
            list.forEach {
                employees.save(it)
            }
            var root = employees.getRoot()
            return printHierarchy(root, 0)
        } catch (e: InvalidInputException) {
            throw e
        }
    }

    /**
     * Returns a pretty json-tree String representation of a full Hierarchy
     *
     * @param root Employee that is root in the Hierarchy
     */
    private fun printHierarchy(root: Employee, level: Int = 0): String {
        var spaces = "".padEnd(level)
        var str = spaces + root.name + "{"
        var children = employees.getChildren(root.id)
        if (children.size > 0) {
            str += "\n"
            children.forEach { element ->
                str += printHierarchy(element, level + 1)
            }
            str += spaces
        }
        str += "}\n"
        return str
    }

    /**
     * Cleans all persisted Employee's data for idempotency.
     */
    fun clean() {
        employees.clean()
    }
}
