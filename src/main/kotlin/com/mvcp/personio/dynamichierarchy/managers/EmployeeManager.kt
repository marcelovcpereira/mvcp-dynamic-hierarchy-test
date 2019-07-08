package com.mvcp.personio.dynamichierarchy.managers

import com.mvcp.personio.dynamichierarchy.entities.Employee
import com.mvcp.personio.dynamichierarchy.exceptions.ConflictingParentException
import com.mvcp.personio.dynamichierarchy.repositories.EmployeeRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
/**
 * Component responsible for persisting and retrieving Employees objects from repository.
 *
 * @author      Marcelo Pereira
 * @version     1.0.0
 * @since       2019-06-08
 */
@Component
class EmployeeManager (
        @Autowired
        val employees: EmployeeRepository
) {
    /**
     * Saves an Employee object. In case the object already exists it performs an Update.
     * If an Employee has a Supervisor, it is not allowed to remove it or change it anymore.
     *
     * @param emp Employee to be created or updated
     */
    fun save(emp : Employee) {
        var exists = employees.findByName(emp.name)
        if (exists.size > 0) {
            if (emp.manager != null) {
                //TODO: this validation could be added as a strategy in InputValidator
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
                employees.save(exists.get(0))
            }
        } else {
            if (emp.manager != null) {
                var existsManager = employees.findByName(emp.manager!!.name)
                var el = Employee("")
                if (existsManager.size < 1) {
                    el = employees.save(Employee(emp.manager!!.name))
                } else {
                    el = existsManager.get(0)
                }
                emp.manager = el
            }
            employees.save(emp)
            println("[DEBUG] Persisted ($emp).")
        }
    }

    /**
     * Retrievess an Employee by name.
     *
     * @param name Name of the Employee to be retrieved
     */
    fun getByName(name: String): Employee? {
        var a = employees.findByName(name)
        if (a.size == 0) return null
        return employees.findByName(name)?.get(0)
    }

    /**
     * Retrievess an Employee's Supervisor by name.
     *
     * @param name Name of the Employee which the Supervisor should be retrieved
     */
    fun getManagerByName(name: String): Employee? {
        var obj = employees.findManagerByName(name)
        if (obj.size > 0) return obj.get(0)
        return null
    }

    /**
     * Deletes all Employee's data from the persistent unit
     */
    fun clean () {
        employees.deleteAll()
        employees.flush()
    }

    /**
     * Returns the Root Employee of a Hierarchy
     */
    fun getRoot(): Employee {
        return employees.findRoot()
    }

    /**
     * Retrieves all subordinated Employee's of an Employee.
     *
     * @param id Id of the Employee which the subordinates should be retrieved
     */
    fun getChildren(id: Long) : List<Employee> {
        return employees.getChildren(id)
    }
}