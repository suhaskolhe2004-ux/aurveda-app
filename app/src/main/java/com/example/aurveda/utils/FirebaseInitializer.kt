package com.example.aurveda.utils

import android.content.Context
import android.util.Log
import com.example.aurveda.BuildConfig
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import com.google.firebase.functions.FirebaseFunctions
import com.google.firebase.storage.FirebaseStorage

/**
 * Centralized initialization for Firebase services.
 * In debug builds, this securely configures the app to use the Firebase Local Emulator Suite.
 * In release builds, this connects to production Firebase.
 */
object FirebaseInitializer {
    private const val TAG = "FirebaseInitializer"
    private const val EMULATOR_HOST = "10.0.2.2" // Default host for Android Emulator loopback
    private var isInitialized = false

    @Synchronized
    fun init(context: Context) {
        if (isInitialized) return

        // Initialize default FirebaseApp
        if (FirebaseApp.getApps(context).isEmpty()) {
            FirebaseApp.initializeApp(context)
        }

        if (BuildConfig.DEBUG) {
            connectToEmulators()
        } else {
            Log.i(TAG, "Release build: Connected to production Firebase")
        }

        isInitialized = true
    }

    private fun connectToEmulators() {
        Log.w(TAG, "Debug build detected: Connecting to Firebase Local Emulator Suite")

        try {
            // Firestore Emulator configuration
            val firestore = FirebaseFirestore.getInstance()
            firestore.useEmulator(EMULATOR_HOST, 8080)

            // Adjust Firestore settings for emulator (disable persistence/cache as standard for dev)
            val settings = FirebaseFirestoreSettings.Builder()
                .setPersistenceEnabled(false)
                .build()
            firestore.firestoreSettings = settings
            Log.d(TAG, "Firestore connected to $EMULATOR_HOST:8080")

            // Authentication Emulator configuration
            FirebaseAuth.getInstance().useEmulator(EMULATOR_HOST, 9099)
            Log.d(TAG, "Auth connected to $EMULATOR_HOST:9099")

            // Storage Emulator configuration
            FirebaseStorage.getInstance().useEmulator(EMULATOR_HOST, 9199)
            Log.d(TAG, "Storage connected to $EMULATOR_HOST:9199")

            // Functions Emulator configuration
            FirebaseFunctions.getInstance().useEmulator(EMULATOR_HOST, 5001)
            Log.d(TAG, "Functions connected to $EMULATOR_HOST:5001")

        } catch (e: Exception) {
            Log.e(TAG, "Failed to connect to Firebase emulators: ${e.message}", e)
        }
    }
}