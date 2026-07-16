# Firebase Local Emulator Suite Configuration

This project is configured to automatically route Firebase traffic to the **Firebase Local Emulator Suite** when running a `debug` build, and strictly connect to production Firebase services for `release` builds.

## How it works
The configuration logic is centralized in `AurvedaApplication` via the `FirebaseInitializer` utility class.

During the `onCreate()` phase of the application:
1. It checks the `BuildConfig.DEBUG` flag.
2. If `true`, it invokes `.useEmulator("10.0.2.2", PORT)` on the Auth, Firestore, Storage, and Functions instances. (10.0.2.2 is the alias Android emulators use to reach the host machine's localhost).
3. If `false` (release builds), this code path is completely ignored, guaranteeing zero chance of emulator code leaking into a production release.

*Note: Firestore persistence/caching is forcefully disabled while connected to the emulator to prevent dirty state caching between sessions.*

## How to start the Emulator Suite
Ensure you have the Firebase CLI installed, then navigate to your Firebase project directory and run:

```bash
firebase emulators:start
```

### Required Ports
Your emulator configuration (`firebase.json`) must align with the following default ports configured in the app:
* **Firestore:** 8080
* **Authentication:** 9099
* **Storage:** 9199
* **Functions:** 5001

## How to verify connection
When launching a debug build, monitor your Logcat in Android Studio for the `FirebaseInitializer` tag. You should see logs confirming successful connections:

```
W/FirebaseInitializer: Debug build detected: Connecting to Firebase Local Emulator Suite
D/FirebaseInitializer: Firestore connected to 10.0.2.2:8080
D/FirebaseInitializer: Auth connected to 10.0.2.2:9099
D/FirebaseInitializer: Storage connected to 10.0.2.2:9199
D/FirebaseInitializer: Functions connected to 10.0.2.2:5001
```

## Physical Devices
If you are running the app on a physical Android device rather than the Android Emulator, `10.0.2.2` will not work. You will need to:
1. Find your development machine's local LAN IP address (e.g., `192.168.1.X`).
2. Temporarily update the `EMULATOR_HOST` constant in `FirebaseInitializer.kt` to that IP.
3. Ensure your `firebase.json` binds the emulators to `0.0.0.0` instead of `localhost` so the device can access them over the network.