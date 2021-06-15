package ru.guybydefault.powergain.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.guybydefault.powergain.model.ExerciseTypeInfo
import ru.guybydefault.powergain.model.TrainingExercise
import ru.guybydefault.powergain.repository.DataRepository

class ExercisesViewModel(val dataRepository: DataRepository) : ViewModel() {

    //TODO date range
    val exercises: MutableLiveData<List<ExerciseTypeInfo>> = MutableLiveData()

    init {
        loadExercisesTypeInfo()
    }

    private fun loadExercisesTypeInfo() {
        exercises.postValue(dataRepository.searchTypesByStr(""))
    }

    fun searchExercises(str: String) {
        exercises.postValue(dataRepository.searchTypesByStr(str))
    }
}