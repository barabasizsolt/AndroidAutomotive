package com.example.polestarinfo.benchmark

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.*
import kotlin.math.sqrt

object Benchmark {
    private const val MAX_ITERATION = 20000000
    private const val MATRIX_SIZE = 800
    private val random = Random()

    suspend fun primalityTest(){
        withContext(Dispatchers.Default) {
            for (i in 1..MAX_ITERATION) {
                isPrime(i)
            }
        }
    }

    suspend fun factorialCalculation(){
        withContext(Dispatchers.Default) {
            for (i in 1..(MAX_ITERATION / 1000)) {
                factorial(i)
            }
        }
    }

    suspend fun sorting(){
        withContext(Dispatchers.Default) {
            val randomList = (1..(MAX_ITERATION / 5)).toList().toIntArray()
            randomList.shuffle()
            randomList.sortDescending()
        }
    }

    suspend fun matrixMultiplication(){
        withContext(Dispatchers.Default) {
            val matrix1 = Array(MATRIX_SIZE) { IntArray(MATRIX_SIZE) }
            for (i: Int in matrix1.indices) {
                for (j: Int in matrix1[i].indices) {
                    matrix1[i][j] = random.nextInt()
                }
            }

            val matrix2 = Array(MATRIX_SIZE) { IntArray(MATRIX_SIZE) }
            for (i: Int in matrix2.indices) {
                for (j: Int in matrix2[i].indices) {
                    matrix2[i][j] = random.nextInt()
                }
            }

            val resMatrix = Array(MATRIX_SIZE) { IntArray(MATRIX_SIZE) }

            for (i in 0 until MATRIX_SIZE) {
                for (j in 0 until MATRIX_SIZE) {
                    for (k in 0 until MATRIX_SIZE) {
                        resMatrix[i][j] = matrix1[i][k] * matrix2[k][j]
                    }
                }
            }
        }
    }

    private fun isPrime(n: Int): Boolean {
        if (n < 2) {
            return false
        }
        if (n == 2 || n == 3) {
            return true
        }
        if (n % 2 == 0 || n % 3 == 0) {
            return false
        }
        val sqrtN = sqrt(n.toDouble()).toLong() + 1
        var i = 6
        while (i <= sqrtN) {
            if (n % (i - 1) == 0 || n % (i + 1) == 0) {
                return false
            }
            i += 6
        }
        return true
    }

    private fun factorial(num: Int) = (1..num).reduce(Int::times)
}