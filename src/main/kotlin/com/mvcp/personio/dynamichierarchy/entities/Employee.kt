package com.mvcp.personio.dynamichierarchy.entities

import javax.persistence.*
import javax.validation.constraints.NotBlank
/**
 * Represents an Employee in the HR hierarchy of Personio.
 * Each Employee has an identification number (id) and a name.
 * Each employee can also be supervised by one another Employee called manager.
 *
 * @author      Marcelo Pereira
 * @version     1.0.0
 * @since       2019-06-08
 */
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
    constructor(str: String) : this(0, str, null)

    constructor (str: String, manager: Employee) : this(0, str, manager)

    override fun toString() : String {
        return "EMPLOYEE(${manager?.id},${manager?.name}) <- ($id, $name)"
    }
}