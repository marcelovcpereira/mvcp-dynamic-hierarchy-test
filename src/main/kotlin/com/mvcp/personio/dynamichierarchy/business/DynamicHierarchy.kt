package com.mvcp.personio.dynamichierarchy.business

import com.mvcp.personio.dynamichierarchy.entities.Employee
import com.mvcp.personio.dynamichierarchy.exceptions.InvalidInputException
import com.mvcp.personio.dynamichierarchy.factories.EmployeeFactory
import com.mvcp.personio.dynamichierarchy.managers.EmployeeManager
import com.mvcp.personio.dynamichierarchy.repositories.HierarchyRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class DynamicHierarchy(
        @Autowired
        val employees: EmployeeManager,

        @Autowired
        val hierarchies: HierarchyRepository,

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
            //TODO create and save hierarchy
        } catch (e: InvalidInputException) {
            return e.toString()
        }

        return "Body was: $body. \nSaved."
    }

    fun list(): MutableList<Employee> {
        return employees.getAll()
    }

    fun clean() {
        employees.clean()
    }
}
