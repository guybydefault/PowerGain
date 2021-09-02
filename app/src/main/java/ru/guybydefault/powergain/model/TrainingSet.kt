package ru.guybydefault.powergain.model

import ru.guybydefault.powergain.calculation.OneRepMaxCalculator

data class TrainingSet(
    /**
     * Kilograms
     */
    val weight: Double,
    val repetitions: Int
) {

    val volume: Double
        get() = weight * repetitions

    fun oneRepMax(oneRepMaxCalculator: OneRepMaxCalculator): Double {
        return oneRepMaxCalculator.calculateOneRepMax(repetitions, weight)
    }

    fun multiply(times: Int): List<TrainingSet> {
        val list = mutableListOf<TrainingSet>()
        for (i in 0..times) {
            list.add(copy(weight, repetitions))
        }
        return list
    }
}
