package com.example.aurveda

import android.app.Application
import com.example.aurveda.utils.FirebaseInitializer

class AurvedaApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        // Centralized initialization logic. Automatically routes to emulators in debug builds.
        FirebaseInitializer.init(this)
    }
}