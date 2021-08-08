package ru.guybydefault.powergain.model

import java.time.LocalDate

data class TrainingExercise(
    val id: Int,
    val type: ExerciseType,
    val date: LocalDate,
    val comment: String?,
    val sets: List<TrainingSet>
) {
    val maxWeight: Double
        get() = sets.maxOf { set -> set.weight }

    val volume
        get() = sets.sumOf { set -> set.volume }

    val repetitions: Int
        get() = sets.sumOf { set -> set.repetitions }

    val intensity: Double
        get() = volume / repetitions

}
