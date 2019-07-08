package com.mvcp.personio.dynamichierarchy.business

import com.mvcp.personio.dynamichierarchy.entities.Employee
import com.mvcp.personio.dynamichierarchy.exceptions.InvalidInputException
import com.mvcp.personio.dynamichierarchy.factories.EmployeeFactory
import com.mvcp.personio.dynamichierarchy.managers.EmployeeManager
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class DynamicHierarchy(
        @Autowired
        val employees: EmployeeManager,

        @Autowired
        val empFactory: EmployeeFactory
) {
    fun getSupervisors(name: String): String {
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

    fun putHierarchy(body: String): String {
        try {
            clean()
            var list = empFactory.buildFromJson(body)
            list.forEach {
                println("[DEBUG] ##### Saving ##### ($it)")
                employees.save(it)
            }
            var root = employees.getRoot()
            return printHierarchy(root, 0)
        } catch (e: InvalidInputException) {
            return e.toString()
        }
    }

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

    fun clean() {
        employees.clean()
    }
}
