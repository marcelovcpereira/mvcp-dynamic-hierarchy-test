package com.mvcp.personio.dynamichierarchy.exceptions
/**
 * Thrown when the Input contains conflicting Supervisor assignment for some Employee
 *
 * @author      Marcelo Pereira
 * @version     1.0.0
 * @since       2019-06-08
 */
class ConflictingParentException(message:String): Exception(message)