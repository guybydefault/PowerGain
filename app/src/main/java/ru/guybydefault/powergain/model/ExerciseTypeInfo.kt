package ru.guybydefault.powergain.model

data class ExerciseTypeInfo(
    val exerciseType: ExerciseType,
    val exercises: List<TrainingExercise>
) {
}