package com.mvcp.personio.dynamichierarchy.controllers
import com.mvcp.personio.dynamichierarchy.business.DynamicHierarchy
import com.mvcp.personio.dynamichierarchy.entities.Employee
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
class Entrypoint (
        @Autowired
        val app: DynamicHierarchy
) {

    @GetMapping("/{name}")
    fun getSupervisors(@PathVariable(value = "name") name: String) : String {
        return app.getSupervisors(name)
    }

    @PostMapping("/")
    fun putHierarchy(@RequestBody body: String) : String {
        println("INPUT BODY: $body")
        return app.putHierarchy(body)
    }

    @GetMapping("/all")
    fun listAll() : MutableList<Employee> {
        return app.list()
    }
}