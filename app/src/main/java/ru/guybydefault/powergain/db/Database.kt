package ru.guybydefault.powergain.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ru.guybydefault.powergain.db.dao.TrainingExerciseDao
import ru.guybydefault.powergain.db.objects.ExerciseTypeEntity
import ru.guybydefault.powergain.db.objects.TrainingExerciseEntity
import ru.guybydefault.powergain.db.objects.TrainingSetEntity
import ru.guybydefault.powergain.model.TrainingSet

@Database(
    entities = [TrainingExerciseEntity::class, ExerciseTypeEntity::class, TrainingSetEntity::class],
    version = 1
)
@TypeConverters(LocalDateConverter::class)
abstract class Database : RoomDatabase() {
    abstract fun trainingExerciseDao(): TrainingExerciseDao
}