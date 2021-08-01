package ru.guybydefault.powergain

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import ru.guybydefault.powergain.repository.DataRepository
import ru.guybydefault.powergain.viewmodel.ChartViewModel
import ru.guybydefault.powergain.viewmodel.CreateTrainingTypeViewModel
import ru.guybydefault.powergain.viewmodel.CreateTrainingViewModel
import ru.guybydefault.powergain.viewmodel.ExercisesViewModel

class PowerGainContainer(val application: PowerGainApplication) {
    val scope = CoroutineScope(SupervisorJob())

    val dataRepository: DataRepository by lazy {
        DataRepository(application, scope)
    }

    val exercisesViewModel = ExercisesViewModel(dataRepository)
    val createTrainingTypeViewModel = CreateTrainingTypeViewModel(dataRepository)
    val createTrainingViewModel = CreateTrainingViewModel(dataRepository)
    val chartViewModel = ChartViewModel(dataRepository)
}