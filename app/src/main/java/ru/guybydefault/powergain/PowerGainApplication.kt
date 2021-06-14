package ru.guybydefault.powergain

import android.app.Application

class PowerGainApplication : Application() {
     lateinit var powerGainContainer: PowerGainContainer

     override fun onCreate() {
          powerGainContainer = PowerGainContainer(this)
          super.onCreate()
     }
}