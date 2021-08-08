package ru.guybydefault.powergain.db.objects

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "training_set", foreignKeys = [
        ForeignKey(
            entity = TrainingExerciseEntity::class,
            parentColumns = ["id"],
            childColumns = ["trainingId"],
            onDelete = CASCADE
        )
    ],
    indices = [Index("trainingId")]
)
data class TrainingSetEntity(
    @PrimaryKey val id: Int,
    /**
     * Kilograms
     */
    val weight: Double,
    val repetitions: Int,
    val trainingId: Int
) {
}
