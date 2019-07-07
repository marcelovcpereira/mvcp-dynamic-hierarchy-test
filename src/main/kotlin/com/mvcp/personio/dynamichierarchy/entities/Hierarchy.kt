package com.mvcp.personio.dynamichierarchy.entities

import javax.persistence.*
import javax.validation.constraints.NotBlank

@Entity
data class Hierarchy (
        @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long = 0,

        @OneToOne @get: NotBlank
        val root: Employee
)