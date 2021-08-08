package ru.guybydefault.powergain.db.objects

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey


@Entity(
    tableName = "exercise_type",
    indices = [Index("name", unique = true)]
)
data class ExerciseTypeEntity(
    @PrimaryKey val id: Int,
    val name: String
) {
}
