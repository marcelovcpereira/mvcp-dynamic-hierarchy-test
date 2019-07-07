package com.mvcp.personio.dynamichierarchy.validators

import com.mvcp.personio.dynamichierarchy.entities.Employee
import com.mvcp.personio.dynamichierarchy.exceptions.MultipleRootsException
import org.springframework.stereotype.Component

@Component
class InputValidator {
    fun validate(array : List<Employee>) {
        println("[DEBUG] Initial list: [")
        array.forEach { element ->
            println("[DEBUG] \t\t$element,")
        }
        println("[DEBUG] ]")
        var elementMap = getElementMap(array)
        println("[DEBUG] Processed list: [")
        elementMap.values.forEach { element ->
            println("[DEBUG] \t\t$element,")
        }
        println("[DEBUG] ]")
        validateSingleRoot(elementMap.values.toList())

        validateNotCyclic(elementMap.values.toList())

    }

    fun validateNotCyclic(array : List<Employee>) {
        var visitedMap = HashMap<String, Boolean>()
        var maxTraversal = array.size
        array.forEach { element ->
            visitedMap.put(element.name, false)
        }

        array.forEach { element ->

        }
        println("[DEBUG] VALID (Not Cyclic)")
    }


    fun validateSingleRoot(array: List<Employee>) {
        var roots = 0
        println("[DEBUG] Validating roots in list: $array")
        array.forEach{ element ->
            if (element.manager == null) {
                println("[DEBUG] Found root on $element, which has no manager: ${element.manager}")
                roots ++
            }
        }
        if (roots > 1) throw MultipleRootsException("Your input contains multiple roots")
        println("[DEBUG] VALID (Single Root tree)")
    }

    fun getElementMap(array : List<Employee>): Map<String, Employee> {
        var result = HashMap<String, Employee>()
        array.forEach { element ->
            if (result.containsKey(element.name)) {
                println("[DEBUG] Exists Element! ($element)")
                var current = result.get(element.name)
                if (current != null) {
                    if (current.manager == null && element.manager != null) {
                        println("[DEBUG] Updating Element ($current)'s parent TO: (${element.manager})")
                        current.manager = element.manager
                        current.level = element.manager!!.level + 1
                    }
                    println("[DEBUG] Skipping element ($element")
                }
            } else {
                println("[DEBUG] New Element! (${element.name},$element)")
                if (result.containsKey(element.manager?.name)) {
                    var manager = result.get(element.manager?.name)
                    if (manager != null) {
                        element.manager = manager
                        element.level = manager.level + 1
                    }
                }
                println("[DEBUG] Persisted element ($element")
                result.put(element.name, element)
            }
        }
        return result
    }
}
