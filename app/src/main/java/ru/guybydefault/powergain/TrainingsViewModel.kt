package ru.guybydefault.powergain

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.guybydefault.powergain.model.TrainingExercise
import ru.guybydefault.powergain.repository.DataRepository
import kotlin.properties.Delegates

class TrainingsViewModel(val dataRepository: DataRepository) :
    ViewModel() {
    val exercises: MutableLiveData<List<TrainingExercise>> = MutableLiveData()
    var exerciseTypeId: Int = -1
        set(value) {
            field = value
            exercises.postValue(dataRepository.getTrainingsByType(exerciseTypeId).sortedByDescending { it.date })
        }

}