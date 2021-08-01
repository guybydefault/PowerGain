package ru.guybydefault.powergain.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.guybydefault.powergain.model.TrainingExercise
import ru.guybydefault.powergain.repository.DataRepository

class ChartViewModel(val dataRepository: DataRepository) : ViewModel()  {
    val highlightedTraining = MutableLiveData<TrainingExercise>()
}