package ru.guybydefault.powergain.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.guybydefault.powergain.model.ExerciseType
import ru.guybydefault.powergain.model.TrainingExercise
import ru.guybydefault.powergain.model.TrainingSet
import ru.guybydefault.powergain.repository.DataRepository

class CreateTrainingViewModel(val dataRepository: DataRepository) : ViewModel() {
    //    val exerciseType = ExerciseType
    val trainingSets = MutableLiveData<List<TrainingSet>>()
    val exerciseType = MutableLiveData<ExerciseType>()
    get() {
        return field
    }
    var exerciseTypeId: Int = -1
        set(value) {
            field = value
            exerciseType.value = dataRepository.getExerciseType(value)
        }
}