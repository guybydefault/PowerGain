package ru.guybydefault.powergain

import android.app.Application
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob


class PowerGainApplication : Application() {
     lateinit var container: PowerGainContainer

     override fun onCreate() {
          container = PowerGainContainer(this)
          super.onCreate()
     }
}