package com.example.agenda_musical_reto1.utils

import android.app.Application
import android.content.Context
import android.content.res.Configuration
import com.example.agenda_musical_reto1.data.User

class MyApp : Application() {
    companion object {
        lateinit var context: Context
        lateinit var userPreferences: UserPreferences

    }

    // Called when the application is starting, before any other application objects have been created.
    // Overriding this method is totally optional!
    override fun onCreate() {
        super.onCreate()
        context = this
        userPreferences = UserPreferences()

        // Required initialization logic here!
    }

    // Called by the system when the device configuration changes while your component is running.
    // Overriding this method is totally optional!
    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
    }

    // This is called when the overall system is running low on memory,
    // and would like actively running processes to tighten their belts.
    // Overriding this method is totally optional!
    override fun onLowMemory() {
        super.onLowMemory()
    }
}