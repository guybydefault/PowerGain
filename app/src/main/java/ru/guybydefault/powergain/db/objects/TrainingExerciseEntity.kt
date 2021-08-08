package ru.guybydefault.powergain.db.objects

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.Index
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity(
    tableName = "training_exercise",
    foreignKeys = [
        ForeignKey(
            entity = ExerciseTypeEntity::class,
            parentColumns = ["id"],
            childColumns = ["typeId"],
            onDelete = CASCADE
        )
    ],
    indices = [Index("typeId")]
)
data class TrainingExerciseEntity(
    @PrimaryKey val id: Int,
    val date: LocalDate,
    val comment: String?,
    val typeId: Int
) {
}