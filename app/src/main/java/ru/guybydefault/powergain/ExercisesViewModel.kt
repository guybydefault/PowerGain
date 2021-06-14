package ru.guybydefault.powergain

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.guybydefault.powergain.model.ExerciseTypeInfo
import ru.guybydefault.powergain.repository.DataRepository

class ExercisesViewModel(val dataRepository: DataRepository) : ViewModel() {

    val exercises: MutableLiveData<List<ExerciseTypeInfo>> = MutableLiveData()

    init {
        loadExercisesTypeInfo()
    }

    private fun loadExercisesTypeInfo() {
        exercises.postValue(dataRepository.trainExercisesTypes)
    }

    fun searchExercises(str: String) {
        exercises.postValue(dataRepository.searchTypesByStr(str))
    }
}