# Little Lemon

Android app for the fictional Little Lemon restaurant, built with Kotlin and Jetpack Compose.

## Features

- Onboarding with email validation; the user is stored locally with Room 3.
- Skips onboarding on launch if a user already exists.
- Home screen with the menu fetched over the network (Ktor), searchable by dish name and filterable by category.
- Profile screen showing the saved user, with log out.

## Tech stack

- Kotlin, Jetpack Compose, Material 3, Navigation Compose
- Room 3 (KSP) for local storage
- Ktor + kotlinx.serialization for networking
- Koin for dependency injection
- Coil for image loading

## Build

Requires Android Studio with the Android SDK installed (`local.properties` is generated locally and not committed).

```
./gradlew assembleDebug
```
