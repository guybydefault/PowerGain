package ru.guybydefault.powergain.calculation

import java.lang.IllegalArgumentException

abstract class AbstractOneRepMaxCalculator : OneRepMaxCalculator {
    override fun calculateOneRepMax(reps: Int, weight: Double): Double {
        if (reps < 1 || weight < 0.0) {
            throw IllegalArgumentException()
        }
        return calculate(reps, weight)
    }

    abstract fun calculate(reps: Int, weight: Double): Double
}