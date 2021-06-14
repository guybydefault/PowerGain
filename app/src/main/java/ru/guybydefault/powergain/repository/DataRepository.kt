package ru.guybydefault.powergain.repository

import ru.guybydefault.powergain.model.ExerciseType
import ru.guybydefault.powergain.model.ExerciseTypeInfo
import ru.guybydefault.powergain.model.TrainingExercise
import ru.guybydefault.powergain.parser.Parser
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.lang.IllegalArgumentException

class DataRepository(inputStream: InputStream) {
    private val _exercises: MutableList<TrainingExercise>
    val exercises: List<TrainingExercise>
        get() = _exercises

    private val _trainingExercisesByType: MutableMap<ExerciseType, MutableList<TrainingExercise>>
    val trainingExercisesByType: Map<ExerciseType, List<TrainingExercise>>
        get() = _trainingExercisesByType

    private val _trainExercisesTypes: MutableList<ExerciseTypeInfo>
    val trainExercisesTypes: List<ExerciseTypeInfo>
        get() = _trainExercisesTypes


    init {
        val parser = Parser(BufferedReader(InputStreamReader(inputStream)))
        _exercises = parser.parse()

        _trainingExercisesByType = initByType()
        _trainExercisesTypes = initTypeInfo()
    }

    fun getTrainingsByType(typeId: Int): List<TrainingExercise> {
        val type = trainingExercisesByType.keys.find { type -> type.id == typeId }
        if (type == null) {
            throw IllegalArgumentException()
        }
        return trainingExercisesByType[type]!!
    }

    fun searchTypesByStr(str: String): List<ExerciseTypeInfo> {
        return trainExercisesTypes.filter {
            it.exerciseType.name.contains(
                str,
                ignoreCase = true
            )
        }.sortedWith(compareByDescending<ExerciseTypeInfo> {
            it.exerciseType.name.startsWith(
                str,
                ignoreCase = true
            )
        }.thenByDescending { it.exercises.size }
        )
    }

    private fun initByType(): MutableMap<ExerciseType, MutableList<TrainingExercise>> {
        val exercisesMap = mutableMapOf<ExerciseType, MutableList<TrainingExercise>>()

        for (ex in exercises) {
            if (!exercisesMap.containsKey(ex.type)) {
                exercisesMap.put(ex.type, mutableListOf())
            }
            exercisesMap.get(ex.type)!!.add(ex)
        }
        return exercisesMap
    }

    private fun initTypeInfo(): MutableList<ExerciseTypeInfo> {
        val trainingExercisesTypes = mutableListOf<ExerciseTypeInfo>()
        for ((key, exercises) in trainingExercisesByType) {
            trainingExercisesTypes.add(ExerciseTypeInfo(key, exercises))
        }
        return trainingExercisesTypes
    }
}