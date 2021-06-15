package ru.guybydefault.powergain

import ru.guybydefault.powergain.repository.DataRepository
import ru.guybydefault.powergain.viewmodel.ExercisesViewModel
import ru.guybydefault.powergain.viewmodel.TrainingsViewModel

class PowerGainContainer(val application: PowerGainApplication) {
    val dataRepository: DataRepository by lazy {
        DataRepository(application.resources.openRawResource(R.raw.training_import))
    }

    val exercisesViewModel = ExercisesViewModel(dataRepository)
    val trainingsViewModel = TrainingsViewModel(dataRepository)
}