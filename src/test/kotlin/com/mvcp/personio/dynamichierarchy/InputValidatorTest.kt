package com.mvcp.personio.dynamichierarchy

import com.mvcp.personio.dynamichierarchy.exceptions.CyclicInputException
import com.mvcp.personio.dynamichierarchy.exceptions.MultipleRootsException
import com.mvcp.personio.dynamichierarchy.factories.EmployeeFactory
import com.mvcp.personio.dynamichierarchy.validators.InputValidator
import org.junit.Test

/**
 * Tests Input Validation strategies
 *
 * @author      Marcelo Pereira
 * @version     1.0.0
 * @since       2019-06-08
 */
class InputValidatorTest : BaseTest() {

    @Test(expected = CyclicInputException::class)
    fun shouldDetectCycle() {
        var validator = InputValidator()
        var parse = EmployeeFactory()
        validator.validateNotCyclic(parse.buildFromJson(CYCLIC_BODY))
    }

    @Test(expected = MultipleRootsException::class)
    fun shouldDetectMultipleRoot() {
        var validator = InputValidator()
        var parse = EmployeeFactory()
        validator.validateSingleRoot(parse.buildFromJson(MULTIPLE_ROOTS_BODY))
    }

    @Test(expected = Test.None::class)
    fun shouldValidate() {
        var validator = InputValidator()
        var parse = EmployeeFactory()
        validator.validate(parse.buildFromJson(VALID_BODY))
    }
}
