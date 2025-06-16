package com.example.myaapp

class Calculator {
    fun add(a: Int, b: Int): Int {
        return a + b
    }

    fun sub(a: Int, b: Int): Int {
        return a - b
    }

    fun divide(a: Int, b: Int): Int? {
        if (b == 0) {
            return null
        }
        return a / b
    }
}