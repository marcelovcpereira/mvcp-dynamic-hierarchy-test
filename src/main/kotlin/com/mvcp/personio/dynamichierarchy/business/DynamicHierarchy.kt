package com.mvcp.personio.dynamichierarchy.business

import com.mvcp.personio.dynamichierarchy.entities.Employee
import com.mvcp.personio.dynamichierarchy.exceptions.ConflictingParentException
import com.mvcp.personio.dynamichierarchy.exceptions.InvalidInputException
import com.mvcp.personio.dynamichierarchy.factories.EmployeeFactory
import com.mvcp.personio.dynamichierarchy.repositories.EmployeeRepository
import com.mvcp.personio.dynamichierarchy.repositories.HierarchyRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class DynamicHierarchy(
        @Autowired
        val employees: EmployeeRepository,

        @Autowired
        val hierarchies: HierarchyRepository,

        @Autowired
        val empFactory: EmployeeFactory
) {
    fun getSupervisors(name: String): String {
        var manager = employees.findManagerByName(name)
        var managerName = "<null>"
        if (manager.size > 0) managerName = manager.get(0)?.name

        var superManager = employees.findManagerByName(managerName)
        var superManagerName = "<null>"
        if (superManager.size > 0) superManagerName = superManager?.get(0)?.name

        return "manager: $managerName, supermanager: $superManagerName"
    }

    fun putHierarchy(body: String): String {
        try {
            var list = empFactory.buildFromJson(body)
            println("PERSISTING LIST: $list")
            list.forEach {
                println("EL:" + it)
                var exists = employees.findByName(it.name)
                if (exists.size > 0) {
                    println("[DEBUG] Exists. Checking its manager...")
                    var el = exists.get(0)
                    if (it.manager != null) {
                        if (el.manager != null) {
                            throw ConflictingParentException("employee $it already has a parent (${el.manager})")
                        }
                        el.manager = it.manager
                        println("[DEBUG] Updating EL: $el with MANAGER: ${it.manager}")
                        employees.save(el)
                    } else {
                        println("[DEBUG] Skipping, already exists...")
                    }
                } else {
                    if (it.manager != null) {
                        var manager = it.manager
                        var name = manager?.name
                        if (name == null) {
                            name = ""
                        }
                        var existsManager = employees.findByName(name)
                        println("[DEBUG] Merging reference of parent to ${existsManager.get(0)}")
                        it.manager = existsManager.get(0)
                    }

                    println("[DEBUG] Saving NEW: $it")
                    employees.save(it)
                }

            }
            //TODO create and save hierarchy
        } catch (e: InvalidInputException) {
            return e.toString()
        }

        return "Body was: $body. \nSaved."
    }

    fun list(): MutableList<Employee> {
        return employees.findAll()
    }
}
