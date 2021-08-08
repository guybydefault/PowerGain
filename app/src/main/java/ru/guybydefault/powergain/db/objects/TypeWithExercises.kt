package ru.guybydefault.powergain.db.objects

import androidx.room.Embedded
import androidx.room.Relation
import ru.guybydefault.powergain.model.ExerciseType


data class TypeWithExercises(
    @Embedded
    val type: ExerciseTypeEntity,
    @Relation(entity = TrainingExerciseEntity::class, parentColumn = "id", entityColumn = "typeId")
    val exercises: List<TrainingExerciseWithSets>
) {
}