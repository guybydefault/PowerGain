package ru.guybydefault.powergain

import androidx.room.Room
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import ru.guybydefault.powergain.repository.TrainingsService
import ru.guybydefault.powergain.viewmodel.ChartViewModel
import ru.guybydefault.powergain.viewmodel.CreateTrainingTypeViewModel
import ru.guybydefault.powergain.viewmodel.CreateTrainingViewModel
import ru.guybydefault.powergain.viewmodel.ExercisesViewModel

class PowerGainContainer(val application: PowerGainApplication) {
    val scope = CoroutineScope(SupervisorJob())

    val trainingsRepository: TrainingsService by lazy {
        TrainingsService(application, scope)
    }

    val db = Room.databaseBuilder(
        application.applicationContext,
        ru.guybydefault.powergain.db.Database::class.java,
        "power-gain-db"
    ).build()

    val exercisesViewModel = ExercisesViewModel(trainingsRepository)
    val createTrainingTypeViewModel = CreateTrainingTypeViewModel(trainingsRepository)
    val createTrainingViewModel = CreateTrainingViewModel(trainingsRepository)
    val chartViewModel = ChartViewModel(trainingsRepository)
}