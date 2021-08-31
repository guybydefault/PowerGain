package ru.guybydefault.powergain.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.guybydefault.powergain.model.ExerciseType
import ru.guybydefault.powergain.model.TrainingExercise
import ru.guybydefault.powergain.repository.TrainingsService

class ExercisesViewModel(val trainingsRepository: TrainingsService) : ViewModel() {

    val exercises: MutableLiveData<List<ExerciseType>> = MutableLiveData()

    lateinit var exerciseType: ExerciseType
    val trainingExercises: MutableLiveData<List<TrainingExercise>> = MutableLiveData()
    var exerciseTypeId: Int = -1
        set(value) {
            field = value
            exerciseType = trainingsRepository.getExerciseType(value)
            trainingExercises.setValue(
                trainingsRepository.getTrainingsByType(exerciseTypeId).sortedByDescending { it.date })
        }

    init {
        loadExercisesTypeInfo()
    }

    //TODO onPeriodChange re-init exercises list & trainingExercises list
    private fun loadExercisesTypeInfo() {
        exercises.setValue(trainingsRepository.searchTypesByStr(""))
    }

    fun searchExercises(str: String) {
        exercises.setValue(trainingsRepository.searchTypesByStr(str))
    }
}