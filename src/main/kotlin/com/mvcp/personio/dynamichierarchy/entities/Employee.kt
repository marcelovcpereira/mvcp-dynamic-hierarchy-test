package com.mvcp.personio.dynamichierarchy.entities

import javax.persistence.*
import javax.validation.constraints.NotBlank

@Entity
data class Employee(
        @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
        var id: Long = 0,

        @Column(unique = true)
        var name: String,

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name="manager_id")
        var manager: Employee?
) {
    constructor(str: String) : this(0, str, null )

    constructor (str: String, manager: Employee) : this(0, str, manager)

    override fun toString() : String {
        return "EMPLOYEE(${manager?.id},${manager?.name}) <- ($id, $name)"
    }

}