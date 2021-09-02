package ru.guybydefault.powergain.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.guybydefault.powergain.model.ExerciseType
import ru.guybydefault.powergain.model.TrainingSet
import ru.guybydefault.powergain.repository.TrainingsService

class CreateTrainingViewModel(val trainingsService: TrainingsService) : ViewModel() {
    val trainingSets = MutableLiveData<MutableList<TrainingSet>>()

    val exerciseType = MutableLiveData<ExerciseType>()
        get() {
            return field
        }

    fun setupViewModelForExerciseType(exerciseTypeId: Int) {
        exerciseType.value = trainingsService.getExerciseType(exerciseTypeId)
        initTrainingSets()
    }

    fun setupViewModelForTraining(trainingId: Int) {
        val training = trainingsService.getTraining(trainingId)!!
        exerciseType.value = training.type
        trainingSets.value = training.sets.toMutableList()
    }

    private fun initTrainingSets() {
        trainingSets.value = mutableListOf()
        val prev =
            trainingsService.getTrainingsByType(exerciseType.value!!.id).maxByOrNull { e -> e.date }
        if (prev != null) {
            val sets = trainingSets.value!!
            sets.add(TrainingSet(prev.sets[0].weight, prev.sets[0].repetitions))
            trainingSets.postValue(sets)
        }
    }
}