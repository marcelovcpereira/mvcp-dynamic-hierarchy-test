package com.mvcp.personio.dynamichierarchy.business

import com.mvcp.personio.dynamichierarchy.entities.Employee
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
        val hierarchies: HierarchyRepository
) {
    fun getSupervisors(name: String): String {
        return "You are querying for: $name"
    }

    fun putHierarchy(body: String): String {
        var emp = EmployeeFactory.buildFromJson(body)

        employees.save(emp)
        return "Body was: $body"
    }

    fun list(): MutableList<Employee> {
        return employees.findAll()
    }
}
