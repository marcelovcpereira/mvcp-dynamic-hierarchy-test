package com.mvcp.personio.dynamichierarchy.managers

import com.mvcp.personio.dynamichierarchy.entities.Employee
import com.mvcp.personio.dynamichierarchy.exceptions.ConflictingParentException
import com.mvcp.personio.dynamichierarchy.repositories.EmployeeRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class EmployeeManager (
        @Autowired
        val employees: EmployeeRepository
) {
    fun save(emp : Employee) {
        var exists = employees.findByName(emp.name)
        if (exists.size > 0) {
            println("[DEBUG] Exists ===${exists.get(0)}===. Checking its manager...")
            if (emp.manager != null) {
                if (exists.get(0).manager != null && exists.get(0).manager!!.name != emp.manager!!.name) {
                    throw ConflictingParentException("employee $emp already has a parent (${exists.get(0).manager})")
                }
                var result = employees.findByName(emp.manager!!.name)
                if (result.size == 0) {
                    employees.save(emp.manager!!)
                } else {
                    emp.manager = result.get(0)
                }
                exists.get(0).manager = emp.manager
                println("[DEBUG] Updating EL: ${exists.get(0)} with MANAGER: ${emp.manager}")
                employees.save(exists.get(0))
            } else {
                println("[DEBUG] Skipping, already exists...")
            }
        } else {
            println("[DEBUG] Not Exists. Checking its manager...")
            if (emp.manager != null) {
                println("[DEBUG] It has manager: ${emp.manager}: ${emp.manager!!.name}")
                var existsManager = employees.findByName(emp.manager!!.name)
                var el = Employee("")
                if (existsManager.size < 1) {
                    println("[DEBUG] Persisting new via parent")
                    el = employees.save(Employee(emp.manager!!.name))
                } else {
                    el = existsManager.get(0)
                }
                println("[DEBUG] Merging reference of parent to ${el}")
                emp.manager = el
            }

            println("[DEBUG] Saving NEW: $emp")
            employees.save(emp)
            println("[DEBUG] Persisted ($emp).")
        }
    }

    fun getByName(name: String): Employee? {
        return employees.findByName(name).get(0)
    }

    fun getById(id: Long): Employee? {
        return employees.findById(id).get()
    }

    fun getManagerByName(name: String): Employee? {
        var obj = employees.findManagerByName(name)
        if (obj.size > 0) return obj.get(0)
        return null
    }

    fun getAll(): MutableList<Employee> {
        return employees.findAll()
    }

    fun clean () {
        employees.deleteAll()
        employees.flush()
    }

    fun getRoot(): Employee {
        return employees.findRoot()
    }

    fun getChildren(id: Long) : List<Employee> {
        return employees.getChildren(id)
    }
}