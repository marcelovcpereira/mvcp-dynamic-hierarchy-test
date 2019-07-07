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
        var manager: Employee?,

        @Column
        var level: Int = 0
) {
    constructor(str: String) : this(0, str, null , 0)

    constructor (str: String, manager: Employee) : this(0, str, manager, manager.level + 1)

    override fun toString() : String {
        return "EMPLOYEE(${manager?.id},${manager?.name}, ${manager?.level}) <- ($id, $name, $level)"
    }

}