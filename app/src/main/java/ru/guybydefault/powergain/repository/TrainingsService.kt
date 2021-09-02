package ru.guybydefault.powergain.repository

import kotlinx.coroutines.*
import ru.guybydefault.powergain.PowerGainApplication
import ru.guybydefault.powergain.R
import ru.guybydefault.powergain.model.ExerciseType
import ru.guybydefault.powergain.model.TrainingExercise
import ru.guybydefault.powergain.parser.Parser
import java.io.*
import java.lang.IllegalArgumentException

class TrainingsService(val application: PowerGainApplication, val coroutineScope: CoroutineScope) {
    companion object {
        val DATA_FILE_NAME = "data.txt"
    }

    private lateinit var _exercises: MutableList<TrainingExercise>
    val exercises: List<TrainingExercise>
        get() = _exercises

    private lateinit var _trainExercisesTypes: MutableList<ExerciseType>
    val trainExercisesTypes: List<ExerciseType>
        get() = _trainExercisesTypes

    init {
//        COPY from resources
        val input = application.resources.openRawResource(
            R.raw.training_import
        ).readBytes()
        val fileOutput = File(application.filesDir.absolutePath + "/data.txt")
        FileOutputStream(fileOutput).use {
            it.write(input)
        }
        runBlocking(coroutineScope.coroutineContext) {
            load()
        }
    }

    fun getTraining(trainingId: Int): TrainingExercise? {
        return exercises.find { it.id == trainingId }
    }

    fun getExerciseType(typeId: Int): ExerciseType {
        val type = trainExercisesTypes.find { type -> type.id == typeId }
        if (type == null) {
            throw IllegalArgumentException()
        }
        return type
    }

    fun getTrainingsByType(typeId: Int): List<TrainingExercise> {
        return getExerciseType(typeId)!!.exercises
    }

    fun searchTypesByStr(str: String): List<ExerciseType> {
        return trainExercisesTypes.filter {
            it.name.contains(
                str,
                ignoreCase = true
            )
        }.sortedWith(compareByDescending<ExerciseType> {
            it.name.startsWith(
                str,
                ignoreCase = true
            )
        }.thenByDescending { it.exercises.size }
        )
    }


    suspend fun load() {
        _exercises = loadFromFile().flatMap { it.exercises }.toMutableList()

        _trainExercisesTypes = initTypeInfo(_exercises)
    }

    private suspend fun loadFromFile(): MutableList<ExerciseType> {
        return coroutineScope {
            async(Dispatchers.IO) {
                val dataFile = File("${application.filesDir}/$DATA_FILE_NAME")
                if (dataFile.exists() && dataFile.isFile) {
                    return@async Parser(dataFile).parse()
                } else {
                    dataFile.createNewFile()
                    return@async mutableListOf<ExerciseType>()
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

    private fun initTypeInfo(trainings: List<TrainingExercise>): MutableList<ExerciseType> {
        val trainingExercisesTypes = mutableSetOf<ExerciseType>()
        for (training in trainings) {
            trainingExercisesTypes.add(training.type)
        }
        return trainingExercisesTypes.toMutableList()
    }
}