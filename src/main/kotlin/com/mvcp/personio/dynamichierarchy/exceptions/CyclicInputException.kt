package com.mvcp.personio.dynamichierarchy.exceptions
/**
 * Thrown when the Input contains cycles in the Supervisor assignment for the Employees
 *
 * @author      Marcelo Pereira
 * @version     1.0.0
 * @since       2019-06-08
 */
class CyclicInputException(message:String): Exception(message)