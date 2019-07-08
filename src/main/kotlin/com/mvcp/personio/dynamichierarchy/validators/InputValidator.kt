package com.mvcp.personio.dynamichierarchy.validators

import com.mvcp.personio.dynamichierarchy.entities.Employee
import com.mvcp.personio.dynamichierarchy.exceptions.CyclicInputException
import com.mvcp.personio.dynamichierarchy.exceptions.MultipleRootsException
import org.springframework.stereotype.Component
/**
 * Component responsible for validating input.
 * It receives a list of Employee's and check if it will produce a valid hierarchy.
 *
 * Currently validations implemented:
 * <ul>
 *     <li>Not Cyclic: Ensures no loops are found in the Hierarchy</li>
 *     <li>Single Root: Ensures the hierarchy contains only one Root Employee</li>
 * </ul>
 * <p>
 *
 * @author      Marcelo Pereira
 * @version     1.0.0
 * @since       2019-06-08
 */
@Component
class InputValidator {
    /**
     * Validates the input
     *
     * @param array List of Employees to be validated
     */
    fun validate(array: List<Employee>) {
        var elementMap = getElementMap(array)

        validateSingleRoot(elementMap.values.toList())
        validateNotCyclic(elementMap.values.toList())
    }

    /**
     * Validates if the list has no cycle.
     *
     * For each of the employees in the hierarchy we try to reach the root by following Parent by Parent.
     * If a node is visited more than 1x before reaching root, we have a cycle.
     *
     * @param array List of Employees to be validated
     */
    fun validateNotCyclic(array: List<Employee>) {
        array.forEach { element ->
            //Creating a map of visited nodes for the current element
            var visited = HashMap<String, Boolean>()
            array.forEach { node ->
                visited.put(node.name,false)
            }
            if (element.manager != null) {
                var parent = element.manager
                var lastElement = element.name
                if (parent != null) {
                    do {
                        parent = findArrayByName(array, parent!!.name)
                        if (parent != null) {
                            if (parent.name.equals(element.name)) {
                                throw CyclicInputException("[ERROR] Your input contains a cycle at (${lastElement})")
                            } else {
                                if (visited.get(parent.name) == true) {
                                    throw CyclicInputException("[ERROR] Your input contains a cycle at (${lastElement})")
                                } else {
                                    visited.put(parent.name, true)
                                }

                                if (parent.manager != null) {
                                    lastElement = parent.name
                                    parent = findArrayByName(array, parent.manager!!.name)
                                } else {
                                    parent = null
                                }
                            }
                        } else {
                            parent = null
                        }
                    } while (parent != null)
                }
            }
        }
        println("[DEBUG] VALID (Not Cyclic)")
    }

    /**
     * Helper method for finding an Employee by name in an array
     * (PS: I could override here the equals on Employee entity, but leaving as TODO)
     *
     * @param array List of Employees
     * @param name Name of Employees being searched
     */
    private fun findArrayByName(array: List<Employee>, name: String): Employee? {
        array.forEach { element ->
            if (element.name.equals(name)) return element
        }
        return null
    }

    /**
     * Validates if the list has only one Root element
     * For the specified Hierarchy the list cannot contain more than 1 Employee without Supervisor.
     *
     * @param array List of Employees to be validated
     */
    fun validateSingleRoot(array: List<Employee>) {
        var roots = 0
        array.forEach { element ->
            if (element.manager == null) {
                roots++
            }
        }
        if (roots > 1) throw MultipleRootsException("Your input contains multiple roots")
    }

    /**
     * Removes unecessary elements from initial list, maitaining the final state of the input, then
     * returns a map Name -> Employee for all employees in the hierarchy
     *
     * @param array List of Employees to be validated
     */
    fun getElementMap(array: List<Employee>): Map<String, Employee> {
        var result = HashMap<String, Employee>()
        array.forEach { element ->
            if (result.containsKey(element.name)) {
                var current = result.get(element.name)
                if (current != null) {
                    if (current.manager == null && element.manager != null) {
                        current.manager = element.manager
                    }
                }
            } else {
                if (result.containsKey(element.manager?.name)) {
                    var manager = result.get(element.manager?.name)
                    if (manager != null) {
                        element.manager = manager
                    }
                }
                result.put(element.name, element)
            }
        }
        return result
    }
}
