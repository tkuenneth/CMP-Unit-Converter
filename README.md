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

This project follows the [current default Kotlin Multiplatform project structure](https://blog.jetbrains.com/kotlin/2026/05/new-kmp-default-structure/) (also described in the [recommended structure docs](https://kotlinlang.org/docs/multiplatform/multiplatform-project-recommended-structure.html)):

- `:shared` — multiplatform library (shared UI + logic), using the [Android KMP library plugin](https://blog.jetbrains.com/kotlin/2026/01/update-your-projects-for-agp9/)
- `:androidApp` — Android application entry point (`com.android.application`, built-in Kotlin; no KMP plugin)
- `:desktopApp` — desktop application entry point
- `iosApp/` — Xcode project consuming the `Shared` framework produced by `:shared`

Because UI is shared with Compose Multiplatform on every client, a single `shared` module is used (no `sharedLogic` / `sharedUI` split).

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
./gradlew :androidApp:installDebug
```

**Desktop (run the app):**

```bash
./gradlew :desktopApp:run
```

**iOS (build for a simulator):**

```bash
xcodebuild -project iosApp/iosApp.xcodeproj -scheme iosApp -configuration Debug -destination 'platform=iOS Simulator,name=iPhone 16e'
```
