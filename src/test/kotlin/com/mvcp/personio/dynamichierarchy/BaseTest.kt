package com.mvcp.personio.dynamichierarchy

import com.mvcp.personio.dynamichierarchy.entities.Employee

abstract class BaseTest {

    companion object {
        const val VALID_BODY = "{\"claudia\":\"andre\",\"aline\":\"marcelo\", \"marcelo\":\"claudia\"}"
        const val CYCLIC_BODY = "{\"aline\":\"marcelo\", \"arthur\":\"aline\", \"marcelo\": \"arthur\"}"
        const val MULTIPLE_ROOTS_BODY = "{\"aline\":\"marcelo\", \"arthur\":\"peter\"}"
        val ANDRE = Employee(0, "andre", null)
        val CLAUDIA = Employee(1, "claudia", ANDRE)
        val MARCELO = Employee(2, "marcelo", CLAUDIA)
        val ALINE = Employee(3, "aline", MARCELO)
        val JOAO = Employee(4, "joao", null)
    }
}
