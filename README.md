# Welcome to CMP Unit Converter

This repo contains a real-world example of a *Compose Multiplatform* app. *CMP Unit Converter* runs on Android, iOS, and the Desktop. As its name suggests, you can convert between various units and scales. While this may provide some value, the main goal of the app and the accompanying series on [dev.to](https://dev.to/tkuenneth/series/30921) is to show how to use Compose Multiplatform and a couple of other multiplatform libraries while focusing on platform integration.

Among others, these libraries are used:

- [Koin](https://insert-koin.io/)
- [Compose Material 3 Adaptive](https://developer.android.com/jetpack/androidx/releases/compose-material3-adaptive)
- [DataStore](https://developer.android.com/kotlin/multiplatform/datastore)
- [Navigation 3 for Compose Multiplatform](https://kotlinlang.org/docs/multiplatform/compose-navigation-3.html)
- [Room](https://developer.android.com/kotlin/multiplatform/room)
- [Lifecycle (ViewModel / Runtime Compose)](https://developer.android.com/jetpack/androidx/releases/lifecycle)
- [Kotlinx DateTime](https://github.com/Kotlin/kotlinx-datetime)

Unlike many other samples, this one has a strong focus on platform integration. For example, on the Desktop, you can access the settings, the *About* dialog, and all top-level destinations from the menu bar. On Android and iOS, a top app bar is used. The app supports dark and light mode on all platforms. Database and configuration files are stored in the preferred locations.

### Noteworthy

This project uses the [new recommended Compose Multiplatform project structure](https://blog.jetbrains.com/kotlin/2026/01/update-your-projects-for-agp9/): a shared multiplatform library module (`:shared`) consumed by separate app modules for Android (`:composeApp`) and desktop (`:desktopApp`), rather than a single module combining the app and shared code. The Android build uses AGP 9.3 with the new Android KMP library plugin in `:shared` and built-in Kotlin support in the app module (no separate Kotlin Android plugin). The iOS app lives in the `iosApp/` Xcode project and consumes the same shared framework.

Toolchain versions live in `gradle/libs.versions.toml` and the Gradle wrapper. Current stack (high level): **Gradle 9.7**, **AGP 9.3.2**, **Kotlin 2.4.10**, **Compose Multiplatform 1.12**. Material3, Adaptive, and Navigation 3 are versioned separately from the CMP core artifacts, as documented in the [CMP 1.12 release notes](https://github.com/JetBrains/compose-multiplatform/releases/tag/v1.12.0).

Apple Silicon is required for the iOS targets in this project: CMP 1.11+ dropped `iosX64`, so only `iosArm64` and `iosSimulatorArm64` are configured.

### A note about icons

The [official Android Compose documentation on Material icons](https://developer.android.com/develop/ui/compose/graphics/images/material) recommends against adding *material-icons-extended* as a direct dependency (it is very large and can significantly increase build time) and instead suggests copying only the icons you need or using [Google Font Icons](https://fonts.google.com/icons) (e.g. as SVG or Android vector drawable). This app follows that approach: the icons in use are stored as vector drawables in `shared/src/commonMain/composeResources/drawable/` and referenced via `AppIcons`. Those icon assets are from the [Material Design Icons](https://github.com/google/material-design-icons) project (Apache License 2.0).

### What's up next?

- Regularly updating dependencies
- Exploring deeper Navigation 3 patterns (more routes, back stack–driven state, and adaptive layouts) as the library evolves

### Run and build from the command line

*IntelliJ IDEA* 2026.2 currently supports the Android Gradle Plugin up to **9.1.0**, while this project uses **AGP 9.3.2**. Until the IDE catches up, use the commands below (or *Android Studio*) to build and run Android. Desktop and iOS Gradle/Xcode workflows are unaffected; `./gradlew` in the IDE terminal works regardless of that AGP ceiling.

**Android (build & install debug):**

```bash
./gradlew :composeApp:installDebug
```

**Desktop (run the app):**

```bash
./gradlew :desktopApp:run
```

**iOS (build for a simulator):**

```bash
xcodebuild -project iosApp/iosApp.xcodeproj -scheme iosApp -configuration Debug -destination 'platform=iOS Simulator,name=iPhone 16e'
```
