package ru.guybydefault.powergain

import ru.guybydefault.powergain.model.TrainingExercise
import ru.guybydefault.powergain.calculation.OneRepMaxCalculator
import java.lang.UnsupportedOperationException

fun List<TrainingExercise>.oneRepMax(oneRepMaxCalculator: OneRepMaxCalculator): Double {
    if (this.isEmpty()) {
        return 0.0
    }
    val type = this[0].type
    if (this.any { it.type != type }) {
        throw UnsupportedOperationException("List can not contain different types of exercises while calclatin one rep max")
    }
    return maxOf { it.sets.maxOf { oneRepMaxCalculator.calculateOneRepMax(it.repetitions, it.weight) } }
}

fun List<TrainingExercise>.maxWeight(): Double {
    return maxOf { it.maxWeight }
}