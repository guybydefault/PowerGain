package ru.guybydefault.powergain.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.guybydefault.powergain.model.ExerciseType
import ru.guybydefault.powergain.model.TrainingSet
import ru.guybydefault.powergain.repository.DataRepository

class CreateTrainingViewModel(val dataRepository: DataRepository) : ViewModel() {
    val trainingSets = MutableLiveData<MutableList<TrainingSet>>()

    val exerciseType = MutableLiveData<ExerciseType>()
        get() {
            return field
        }
    var exerciseTypeId: Int = -1
        set(value) {
            field = value
            exerciseType.value = dataRepository.getExerciseType(value)
            initSets()
        }

    private fun initSets() {
        trainingSets.value = mutableListOf()
        /** Init by previous training */
        val prev = dataRepository.getTrainingsByType(exerciseTypeId).maxByOrNull { e -> e.date }
        if (prev != null) {
            val sets = trainingSets.value!!
            sets.add(TrainingSet(prev.sets[0].weight, 0))
            trainingSets.postValue(sets)
        }
    }
}