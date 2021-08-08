package ru.guybydefault.powergain.db.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import ru.guybydefault.powergain.db.objects.TypeWithExercises

@Dao
abstract class TrainingExerciseDao(
) {
    @Query("SELECT * FROM exercise_type")
    @Transaction
    abstract fun getAll(): List<TypeWithExercises>
}