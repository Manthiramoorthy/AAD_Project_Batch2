package com.example.myaapp

import org.junit.Assert
import org.junit.Before
import org.junit.Test

class CalculatorTest {
    lateinit var calculator: Calculator

    @Before
    fun setup() {
        calculator = Calculator()
    }

//    Given When Then

    @Test
    fun `Given call add method when valid input Then should return addition two number`() {
        val a = 10
        val b = 20
        val add = a + b
        val result = calculator.add(a, b)
        Assert.assertSame(add, result)
    }

    @Test
    fun `Given call sub method when valid input Then return subtraction two number`() {
        val a = 10
        val b = 20
        val add = a - b
        val result = calculator.sub(a, b)
        Assert.assertSame(add, result)
    }

    @Test
    fun `Given call divide method when valid input Then return division of two number`() {
        val a = 10
        val b = 20
        val expected = a / b
        val result = calculator.divide(a, b)
        Assert.assertEquals(expected, result)
    }

    @Test
    fun `Given call divide method When second number is zero Then return null`() {
        val a = 10
        val b = 0
        val expected = null
        val result = calculator.divide(a, b)
        Assert.assertEquals(expected, result)
    }
}