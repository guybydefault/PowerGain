package ru.guybydefault.powergain.db.objects

import androidx.room.Embedded
import androidx.room.Relation

data class TrainingExerciseWithSets(
    @Embedded
    val trainingExercise: TrainingExerciseEntity,
    @Relation(
        entity = TrainingSetEntity::class,
        parentColumn = "id",
        entityColumn = "trainingId"
    )
    val sets: List<TrainingSetEntity>
) {
}