package com.mvcp.personio.dynamichierarchy.entities

import javax.persistence.*
import javax.validation.constraints.NotBlank

@Entity
data class Employee(
        @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long = 0,

        val name: String,

        @OneToMany
        val employees: MutableList<Employee> = ArrayList<Employee>()

) {
    constructor(str: String) : this(0, str, ArrayList<Employee>())

    fun addEmployee(employee: Employee) {
        employees.add(employee)
    }

    override fun toString() : String {
        return "$name : {\n\t\t" + employees.forEach{it.toString()} + "\n}"
    }

}