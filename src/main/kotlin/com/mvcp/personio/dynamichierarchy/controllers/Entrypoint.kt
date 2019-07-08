package com.mvcp.personio.dynamichierarchy.controllers
import com.mvcp.personio.dynamichierarchy.business.DynamicHierarchy
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
/**
 * Entrypoint is the unique controller of the Dynamic Hierarchy Application
 * It is responsible for intercepting all incoming HTTP requests and delegate to the App logic handler
 *
 * @author      Marcelo Pereira
 * @version     1.0.0
 * @since       2019-07-08
 */
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
        println("[DEBUG] INPUT BODY: $body")
        return app.putHierarchy(body)
    }
}