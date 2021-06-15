package ru.guybydefault.powergain

import android.app.Application

class PowerGainApplication : Application() {
     lateinit var container: PowerGainContainer

     override fun onCreate() {
          container = PowerGainContainer(this)
          super.onCreate()
     }
}