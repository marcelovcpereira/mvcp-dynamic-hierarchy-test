package com.mvcp.personio.dynamichierarchy.exceptions
/**
 * Thrown when the Input contains references to Non-existant Employee(s)
 *
 * @author      Marcelo Pereira
 * @version     1.0.0
 * @since       2019-06-08
 */
class EmployeeNotFoundException(message:String): Exception(message)