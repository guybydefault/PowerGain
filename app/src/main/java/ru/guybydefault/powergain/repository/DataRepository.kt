package ru.guybydefault.powergain.repository

import kotlinx.coroutines.*
import ru.guybydefault.powergain.PowerGainApplication
import ru.guybydefault.powergain.R
import ru.guybydefault.powergain.model.ExerciseType
import ru.guybydefault.powergain.model.ExerciseTypeInfo
import ru.guybydefault.powergain.model.TrainingExercise
import ru.guybydefault.powergain.parser.Parser
import java.io.*
import java.lang.IllegalArgumentException

class DataRepository(val application: PowerGainApplication, val coroutineScope: CoroutineScope) {
    companion object {
        val DATA_FILE_NAME = "data.txt"
    }

    private lateinit var _exercises: MutableList<TrainingExercise>
    val exercises: List<TrainingExercise>
        get() = _exercises

    private lateinit var _trainingExercisesByType: MutableMap<ExerciseType, MutableList<TrainingExercise>>
    val trainingExercisesByType: Map<ExerciseType, List<TrainingExercise>>
        get() = _trainingExercisesByType

    private lateinit var _trainExercisesTypes: MutableList<ExerciseTypeInfo>
    val trainExercisesTypes: List<ExerciseTypeInfo>
        get() = _trainExercisesTypes

    init {
//        COPY from resources
//        val input = application.resources.openRawResource(
//            R.raw.training_import
//        ).readBytes()
//        val fileOutput = File(application.filesDir.absolutePath + "/data.txt")
//        FileOutputStream(fileOutput).use {
//            it.write(input)
//        }
        runBlocking(coroutineScope.coroutineContext) {
            load()
        }
    }

    fun getExerciseType(typeId: Int): ExerciseType {
        val type = trainingExercisesByType.keys.find { type -> type.id == typeId }
        if (type == null) {
            throw IllegalArgumentException()
        }
        return type
    }

    fun getTrainingsByType(typeId: Int): List<TrainingExercise> {
        return trainingExercisesByType[getExerciseType(typeId)]!!
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


    suspend fun load() {
        _exercises = loadFromFile()

        _trainingExercisesByType = initByType()
        _trainExercisesTypes = initTypeInfo()
    }

    private suspend fun loadFromFile(): MutableList<TrainingExercise> {
        return coroutineScope {
            async(Dispatchers.IO) {
                val dataFile = File("${application.filesDir}/$DATA_FILE_NAME")
                if (dataFile.exists() && dataFile.isFile) {
                    return@async Parser(dataFile).parse()
                } else {
                    dataFile.createNewFile()
                    return@async mutableListOf<TrainingExercise>()
                }
            }.await()
        }
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