package ru.guybydefault.powergain.model

import java.time.LocalDate
import java.time.LocalTime
import java.time.ZonedDateTime

data class TrainingExercise(
    val type: ExerciseType,
    val sets: List<TrainingSet>,
    val time: LocalTime?,
    val date: LocalDate,
    val comment: String?
) {
    val maxWeight : Double
        get() = sets.maxOf { set -> set.weight }

    val volume
        get() = sets.sumOf { set -> set.volume }

    val repetitions : Int
        get() = sets.sumOf { set -> set.repetitions }

    val intensity: Double
        get() = volume / repetitions
}
