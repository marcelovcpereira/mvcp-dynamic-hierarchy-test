package com.mvcp.personio.dynamichierarchy.exceptions
/**
 * Thrown when the Input contains multiple Employees without Supervisors
 *
 * @author      Marcelo Pereira
 * @version     1.0.0
 * @since       2019-06-08
 */
class MultipleRootsException(message:String): Exception(message)