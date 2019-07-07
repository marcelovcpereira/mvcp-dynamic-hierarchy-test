package com.mvcp.personio.dynamichierarchy.repositories

import com.mvcp.personio.dynamichierarchy.entities.Employee
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface EmployeeRepository : JpaRepository<Employee, Long>