package ru.guybydefault.powergain.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.guybydefault.powergain.model.ExerciseType
import ru.guybydefault.powergain.model.ExerciseTypeInfo
import ru.guybydefault.powergain.model.TrainingExercise
import ru.guybydefault.powergain.repository.DataRepository

class ExercisesViewModel(val dataRepository: DataRepository) : ViewModel() {

    val exercises: MutableLiveData<List<ExerciseTypeInfo>> = MutableLiveData()

    lateinit var exerciseType: ExerciseType
    val trainingExercises: MutableLiveData<List<TrainingExercise>> = MutableLiveData()
    var exerciseTypeId: Int = -1
        set(value) {
            field = value
            exerciseType = dataRepository.getExerciseType(value)
            trainingExercises.postValue(
                dataRepository.getTrainingsByType(exerciseTypeId).sortedByDescending { it.date })
        }

    init {
        loadExercisesTypeInfo()
    }

    //TODO onPeriodChange re-init exercises list & trainingExercises list
    private fun loadExercisesTypeInfo() {
        exercises.postValue(dataRepository.searchTypesByStr(""))
    }

    fun searchExercises(str: String) {
        exercises.postValue(dataRepository.searchTypesByStr(str))
    }
}