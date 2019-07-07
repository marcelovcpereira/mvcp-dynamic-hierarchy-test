package com.mvcp.personio.dynamichierarchy.repositories

import com.mvcp.personio.dynamichierarchy.entities.Employee
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface EmployeeRepository : JpaRepository<Employee, Long> {
    @Query("SELECT e FROM Employee e where e.name = :name")
    fun findByName(@Param("name") name: String): List<Employee>


    @Query("SELECT e FROM Employee e where e.id in (SELECT f.manager from Employee f where f.name = :name)")
    fun findManagerByName(@Param("name") name: String): List<Employee>
}