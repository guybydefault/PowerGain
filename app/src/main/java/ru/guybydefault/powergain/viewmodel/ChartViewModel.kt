package ru.guybydefault.powergain.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.guybydefault.powergain.model.TrainingExercise
import ru.guybydefault.powergain.repository.TrainingsService

class ChartViewModel(val trainingsRepository: TrainingsService) : ViewModel()  {
    val highlightedTraining = MutableLiveData<TrainingExercise>()
}