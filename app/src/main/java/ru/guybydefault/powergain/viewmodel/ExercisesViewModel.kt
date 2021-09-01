package ru.guybydefault.powergain.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.guybydefault.powergain.model.ExerciseType
import ru.guybydefault.powergain.model.TrainingExercise
import ru.guybydefault.powergain.repository.TrainingsService

class ExercisesViewModel(val trainingsRepository: TrainingsService) : ViewModel() {

    val exercises: MutableLiveData<List<ExerciseType>> = MutableLiveData()

    val trainingExercises: MutableLiveData<List<TrainingExercise>> = MutableLiveData()

    lateinit var exerciseType: ExerciseType
    private var exerciseTypeId: Int = -1


    init {
        loadExercisesTypeInfo()
    }

    fun setupViewModel(exerciseTypeId: Int) {
        this.exerciseTypeId = exerciseTypeId
        exerciseType = trainingsRepository.getExerciseType(exerciseTypeId)
        trainingExercises.value =
            trainingsRepository.getTrainingsByType(exerciseTypeId).sortedByDescending { it.date }
    }

    //TODO onPeriodChange re-init exercises list & trainingExercises list
    private fun loadExercisesTypeInfo() {
        exercises.setValue(trainingsRepository.searchTypesByStr(""))
    }

    fun searchExercises(str: String) {
        exercises.setValue(trainingsRepository.searchTypesByStr(str))
    }
}