package com.boudour.tasks

import com.boudour.tasks.util.InputValidator
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class InputValidationTest {
    @Test
    fun inputValidator_returnFalseWhenEmpty(){
        //perform actions
        val result = InputValidator.isInputValid("")

        //assert the result
        assertFalse(result)
    }
    @Test
    fun inputValidator_returnFalseWhenNull(){
        //perform actions
        val result = InputValidator.isInputValid(null)

        //assert the result
        assertFalse(result)
    }
    @Test
    fun inputValidator_returnFalseWhenOnlyWhitespace(){
        //perform actions
        val result = InputValidator.isInputValid("    ")

        //assert the result
        assertFalse(result)
    }

    @Test
    fun inputValidator_returnTrueWhenMoreThanOneNonWhiteSpaceCharacter(){
        //perform actions
        val result = InputValidator.isInputValid("more than one charecter")

        //assert the result
        assertTrue(result)
    }
    @Test
    fun inputValidator_returnTrueWhenTwoNonWhiteSpaceCharacter(){
        //perform actions
        val result = InputValidator.isInputValid("ab")

        //assert the result
        assertTrue(result)
    }

    @Test
    fun inputValidator_returnFalseWhenOnlyOneWhiteSpaceCharacter  (){
        //perform actions
        val result = InputValidator.isInputValid("a")

        //assert the result
        assertFalse(result)
    }
}