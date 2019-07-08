package com.mvcp.personio.dynamichierarchy.repositories

import com.mvcp.personio.dynamichierarchy.entities.Employee
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
/**
 * Component responsible for storing/retrieving Employee's into a SQL JPA repository
 *
 * @author      Marcelo Pereira
 * @version     1.0.0
 * @since       2019-06-08
 */
@Repository
interface EmployeeRepository : JpaRepository<Employee, Long> {
    /**
     * Searches an Employee by name
     *
     * @param name Name of the Employee to be retrieved
     */
    @Query("SELECT e FROM Employee e where e.name = :name")
    fun findByName(@Param("name") name: String): List<Employee>

    /**
     * Searches an Employee's Manager by name
     *
     * @param name Name of the Employee which the Supervisor should be retrieved
     */
    @Query("SELECT e FROM Employee e where e.id in (SELECT f.manager from Employee f where f.name = :name)")
    fun findManagerByName(@Param("name") name: String): List<Employee>

    /**
     * Searches the Root Employee
     */
    @Query("SELECT e FROM Employee e where e.manager = null")
    fun findRoot() : Employee

    /**
     * Retrieves the subordinates of an Employee
     *
     * @param id ID of the Employee which the subordinates should be retrieved
     */
    @Query("SELECT e FROM Employee e where e.manager.id = :id")
    fun getChildren(@Param("id") id: Long): List<Employee>
}