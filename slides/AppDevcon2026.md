# Refuelling your Jetpack

### Refuelling your Jetpack

**Speaker Notes:**
Welcome to "Refuelling your Jetpack."

If you are an Android developer, you know Jetpack. It changed how we build Android apps. 

But the ecosystem is shifting again. We aren't just building for Android anymore; we are building for the world. Today, I want to show you how to take the Jetpack libraries you already know, and use them to fuel a new generation of applications that run everywhere.

### What is Android Jetpack?

* More than code: libraries, tools, and guidance for less boilerplate, clearer structure
* Unbundled from the OS: new features without waiting for users to upgrade their devices
* Opinionated: instead of the old "do it however you want" chaos we now have a standard way to build
* Backward compatibility built in: Fragmentation and OS differences are handled for you

**Speaker Notes:**
First, let's ground ourselves. What exactly is Jetpack?
It’s more than just a bag of libraries. It is Google's opinionated answer to Android development.
It unbundled features from the OS, so we could update our apps without waiting for Android system updates. It gave us backward compatibility.
Most importantly, it gave us "Guidance." It stopped the wild west of Android dev and gave us a standard way to build.

### The Era of Fragmentation

* New features used to be tied to OS updates, but OS updates took ages to reach devices
* The Android Support Library (`android.support.*`) was Google's band-aid. It helped, but…
* …it turned into a mess. `v4`, `v7`, `v13`… jar files everywhere. Dependency Hell.

**Speaker Notes:**
But before Jetpack, we lived in an era of Fragmentation.
New features were tied to the OS. If you wanted the latest UI on an older phone, you were out of luck.
Google gave us the "Support Library" to fix this. It worked, but it was a mess. We had `v4` support, `v7` appcompat, `v13`... it was "Dependency Hell." We needed a reboot.

### The Reboot – Enter Android Jetpack

* 2017: Architecture Components (ViewModel, Room, Lifecycle, LiveData) announced at I/O; 1.0 that November
* 2018: Jetpack and the great reboot to `androidx.*`—libraries unbundled, own release cycles, semantic versioning
* Opinionated architecture: from "do it however" to "here's the best way"

**Speaker Notes:**
The reboot came in two steps. In 2017, Google announced **Android Architecture Components** at I/O—ViewModel, Room, Lifecycle, LiveData—and they went 1.0 stable that November. That was the "how to architect" piece. Then at I/O 2018 came **Jetpack**: the umbrella name and the migration to `androidx.*`. Everything moved to the new namespace. Libraries were strictly unbundled—own versions, own cycles, semantic versioning so we could reason about compatibility. So: Architecture Components in 2017, Jetpack and androidx in 2018.

### Fast forward to the Jetpack Jungle

* Today there are 130+ artifacts: not just modularity; instead, it's baggage
* Zombie libraries: deprecated, duplicate, still haunting the docs—e.g. `lifecycle-extensions` (deprecated, split into separate lifecycle-* libs), `security-crypto` (deprecated, no clear successor)
* How to build a modern architecture with three ways to do the same thing?

**Speaker Notes:**
Fast forward to today. We have solved one problem but created another: **The Jetpack Jungle.**
There are now over 130 artifacts in the suite. We have historical baggage. Deprecated libraries still sit next to modern ones—for example **lifecycle-extensions**, which Google deprecated and replaced with separate lifecycle-runtime, lifecycle-viewmodel, and so on, but it still turns up in old tutorials. Or **security-crypto**, deprecated with no clear official successor, but still in the docs. We have three different ways to do background work, two ways to do navigation, and endless UI toolkits. The challenge is no longer "how do I do this?" but "which of these 130 libraries should I actually use?"

### The Official Architecture

* Google's answer to the chaos: a curated stack (the Golden Path)
* UI reacts to state, events flow up. ViewModels hold state and survive config changes
* One navigation graph. One back stack. No reimplementing per screen.
* Reactive, typed persistence: Room for the DB, DataStore for preferences / configuration

**Speaker Notes:**
To survive the jungle, we need a map. A **Golden Path**, if you will.
It’s the curated, opinionated stack that Google actually recommends today. Stick to it and you avoid the jungle.

* **UI:** Reactive, driven by state.
* **Presentation:** ViewModels that survive config changes.
* **Navigation:** A single graph defining your flow.
* **Data:** Room for database, DataStore for preferences.

That's the standard modern Android architecture.

### The Twist

* Standard Android architecture? Isn't this a Cross-Platform track talk?
* That whole stack runs on iOS, Desktop, and Web. Same APIs. One architecture, same code. Everywhere.

**Speaker Notes:**
So, everything I just showed you is the gold standard for Android development.
But looking at the schedule... we are in the **Cross-Platform track**. So why am I spending ten minutes talking about Android libraries?
Here is the twist: **I’m not just talking about Android.**
Everything you just saw on that list—Compose, ViewModels, Navigation, Room, DataStore—is now fully functional in Kotlin Multiplatform. You can take that exact same architecture, those exact same APIs, and compile them natively for iOS, Desktop, and the Web. One architecture, same code, everywhere.

### Compose Multiplatform

* Same Jetpack Compose that every Android dev loves, just unchained from Android
* Declarative, reactive. One mental model for every screen. On iOS and Desktop it uses Skia and runs natively
* No more learning three UI frameworks. Android skills deploy to iOS, Desktop, and Web

**Speaker Notes:**
The UI layer is **Compose Multiplatform**.
It is the exact same declarative, reactive paradigm you use on Android, just unbundled from the OS.
You write composables, and they react to state. On Android, it's Jetpack Compose. On iOS and Desktop, it uses the Skia graphics engine to draw pixel-perfect UI while running natively on the hardware.
This means you aren't learning three different UI frameworks. You aren't context-switching. You are taking your existing Android expertise and applying it to the entire world.

### State & Lifecycle: Adopting the Standard

* Android didn't invent the ViewModel; others had it years ago. But now it's the standard.
* You put ViewModels in `commonMain`. They survive config changes everywhere.
* Same API as on Android. State survives rotation and backgrounding on all platforms.

**Speaker Notes:**
Let's be honest: **Android didn't invent the ViewModel.**
Cross-platform frameworks like Xamarin were using the MVVM pattern to share logic between iOS and Android long before Jetpack existed.
The good news is that **Android and KMP have finally adapted this proven standard.**
You put your `ViewModel` in shared code. It survives configuration changes. It holds your state. It’s the industry standard for a reason, and now it’s the standard for our shared code too.

### Navigation

* With **Navigation Compose** you define your graph and routes once in `commonMain`. Type-safe.
* Back stack and deep links work the same on Android, iOS, and Desktop
* Activities, View Controllers, and windows are just thin hosts; the shared code drives the UI

**Speaker Notes:**
Navigation used to be where cross-platform architectures fell apart. But not here.
With **Navigation Compose**, your navigation graph travels with you. You declare your routes, your arguments, your back stack, and your deep links **once** in shared code. Type-safe.
Whether it's an Android Activity, an iOS View Controller, or a Desktop Window, the platform code is just a thin container hosting your navigation. You aren't reimplementing routing logic three times. You define the flow once, and it drives the UI everywhere.

### Persistence: Room

* Room is an ORM on SQLite: Kotlin classes → tables, DAOs, Flow for reactive queries. Same in KMP.
* Compile-time SQL verification—no nasty runtime surprises. Runs on each platform's native SQLite.
* One database layer in shared code. ViewModels consume it the same way everywhere.

**Speaker Notes:**
For local database storage, we use **Room**.
If you haven't used it before, Room is a full **Object-Relational Mapper (ORM)** wrapping SQLite. It lets you define your data as simple Kotlin objects and maps them automatically to database tables.
Its superpower is **compile-time verification**. Unlike many ORMs that fail at runtime, Room checks your SQL queries against your schema as you build.
In this architecture, Room runs natively on iOS, Android, and Desktop, giving you a single, type-safe data layer with the performance of raw SQLite.

### Configuration & Preferences: DataStore

* DataStore allows typed, async reading / writing of preferences and configuration files
* Not blocking the UI thread
* Key-value or protocol buffers. Same API on every platform. Settings, feature flags, prefs—all in shared code.
* No more UserDefaults for iOS and SharedPreferences for Android

**Speaker Notes:**
Every app needs to store configuration—whether it's a dark mode toggle, a session token, or feature flags.
Usually, this means writing one implementation for iOS using `UserDefaults` and another for Android using `SharedPreferences`.
With **DataStore**, we unify this. It is a modern, multiplatform key-value store built entirely on **Kotlin Coroutines**. It is asynchronous by default, preventing UI freezes on any platform. You write your preference logic once in shared code, and it handles the native storage details for you.

### Three platforms, one architecture

* One shared module holds almost everything: UI, ViewModels, navigation, Room, DataStore
* Platform apps are thin shells—Android, iOS, Desktop. They host and build. That's it.
* Same logic, same screens. No duplicate business logic across platforms.

**Speaker Notes:**
So, how does this fit together?
You have one **Shared Core**. This module contains almost everything: your Compose UI, your Navigation graph, your ViewModels, and your Database.
Surrounding that, you have **Thin Native Shells**. The Android App, the iOS App, and the Desktop App. They do little more than initialize the process and host the shared UI.
They might add a splash screen or handle push notifications, but the actual application—the screens and the logic—is shared. This means no duplicate business logic and no synchronization issues between platforms.

### The Shared Module Structure

* commonMain has it all: Compose UI, ViewModels, Navigation, Room, DataStore.
* When you need something platform-specific (file paths, open URL, back button), you use expect/actual.
* One source of truth. Platform details stay at the edges.

**Speaker Notes:**
The shared module is the structural center of your application.
Inside `commonMain`, you place the core components we just discussed.
But when you need to interact with specific OS APIs—like file paths, Bluetooth, or system intents—you use the `expect/actual` pattern. You declare the interface in the shared code, and the platform module provides the implementation. This keeps your business logic pure and testable, ensuring platform details don't leak into your core architecture.

### Platform Entry Points

* Android: Activity + setContent
* iOS: a view controller hosting Compose
* Desktop: main() and one or more windows
* Each one wires lifecycle and hosts the same root composable
* You init your DI at startup; the rest is shared

**Speaker Notes:**
The platform modules are intentionally thin.
On Android, you have a single Activity calling `setContent`.
On iOS, you have a standard View Controller that hosts the shared Compose UI.
On Desktop, it's just a `main()` function.
Crucially, this is where you initialize your Dependency Injection—like Koin. You wire it up once at startup, and then the rest of the application logic is fully shared.

### Dependency Injection: The Hilt Question

* Hilt is the recommended DI on Android. But Hilt is Dagger and annotation processing. 
* It doesn't run on iOS or Desktop
* Google hasn't announced a multiplatform roadmap for Hilt. So for shared code today, we use a pure Kotlin option (for example, Koin)
* You define modules in shared code; platform supplies the actuals (e.g. where's the data dir). One container, init once per platform

```kotlin
// commonMain: one module, platform supplies DB path via expect/actual
val appModule = module {
    single<AppDatabase> { getRoomDatabase(getDatabaseBuilder()) }  // getDatabaseBuilder() is expect/actual
    viewModelOf(::AppViewModel)
    viewModelOf(::TemperatureViewModel)
    viewModelOf(::DistanceViewModel)
}
```

**Speaker Notes:**
I know what many of you are thinking: "Wait, isn't Hilt the recommended Jetpack DI?"
Yes, for Android, it is fantastic. But Hilt relies heavily on Dagger and Java annotation processing, which simply does not work on iOS or Desktop.
Critically, **Google has not yet said anything about Hilt going multiplatform.** There is no roadmap.
So, to keep our shared code clean and compile-safe *today*, we use a pure Kotlin solution like **Koin**. You define your modules in shared code; each platform supplies the actuals (e.g. where's the data directory). One container, init once per platform. It effectively becomes the standard for this architecture by necessity.

### Real World Example: CMP Unit Converter

* A real app: unit conversion (temperature, distance, etc.) on Android, iOS, and Desktop
* Built on the full Golden Path: Compose, ViewModel, Room, DataStore, Koin
* One codebase, minimal platform glue

**Speaker Notes:**
Enough theory. Let's look at the code.
I built an app called **CMP Unit Converter** to prove this stack works. It converts temperatures and distances, stores your history, and remembers your preferences.
It runs on Android, iOS, and Desktop. But crucially, it is built entirely on the stack we just discussed: Compose for UI, ViewModels for state, Room for history, and Koin for injection. Let's tear it apart and see how it works.

### Project Structure (AGP 9 in Practice)

* Your Android app is its own module now. No KMP plugin in the app—shared is a library. In this repo: shared + composeApp (Android) + desktopApp + iosApp (Xcode).
* In shared: new plugin `com.android.kotlin.multiplatform.library`, and you use `androidLibrary { }` inside the `kotlin { }` block—not the old top-level `android { }`. The app module gets built-in Kotlin from AGP; you don't apply the Kotlin Android plugin there.
* Gradle 9.1, AGP 9. Mental shift, but it pays off. The host apps are thin; the library does the heavy lifting.

```kotlin
// shared/build.gradle.kts
plugins {
    alias(libs.plugins.androidKotlinMultiplatformLibrary)
    // ...
}
kotlin {
    androidLibrary { namespace = "..."; ... }
    // ...
}

// composeApp/build.gradle.kts
dependencies {
    implementation(project(":shared"))
    // ...
}
```

**Speaker Notes:**
When you open the project, you'll notice the structure immediately. It follows the modern AGP 9 guidelines. Gradle 9.1 and AGP 9 are the baseline. In this repo the modules are: shared (the library), composeApp (the Android app), desktopApp, and iosApp is a separate Xcode project.
Your Android app—here, composeApp—is its own module: just application code, no Kotlin Multiplatform plugin. The shared module uses the new `com.android.kotlin.multiplatform.library` plugin. In shared's build file you configure the Android target with `androidLibrary { }` inside the `kotlin { }` block, not the old top-level `android { }` block. The app module uses AGP's built-in Kotlin, so you don't apply the Kotlin Android plugin there. Android is just another target, like Desktop or iOS.
This clean separation is key: the app is a thin shell, the library does the heavy lifting. It's a bit of a mental shift if you had one composeApp doing everything, but it pays off in clearer separation and faster builds.

### Shared UI & Resources

* ConverterScreen.kt lives in commonMain. One UI implementation for all three platforms.
* Icons and strings go in composeResources. You reference them with the generated Res API—Res.string.app_name, Res.drawable.ic_thermostat.
* No XML for Android and Localizable.strings for iOS. One place, native look everywhere.

```kotlin
// commonMain/.../ConverterScreen.kt
@Composable
fun ConverterScreen(
    viewModel: AbstractConverterViewModel,
    scrollBehavior: TopAppBarScrollBehavior
) { ... }

// Res API (e.g. AppIcons.kt)
val Thermostat: DrawableResource get() = Res.drawable.ic_thermostat
// In composables: stringResource(Res.string.app_name)
```

**Speaker Notes:**
In `commonMain`, we have `ConverterScreen.kt`. This is our UI. It's standard Compose code.
But notice the resources. We don't have XML strings for Android and Localizable.strings for iOS. We put everything in the `composeResources` folder. We access them in Kotlin using the generated `Res` object—`Res.string` or `Res.drawable`.
At compile time, CMP bundles these into the correct native format for each platform. You write the UI once, and it looks native everywhere.

### ViewModel & Koin

* The converter ViewModels (e.g. TemperatureViewModel, DistanceViewModel) hold the math and state. Defined in a Koin module in commonMain; the UI gets them via koinViewModel().
* They don't know (or care) if they're running on an iPhone or a Pixel. Same classes everywhere.

```kotlin
// commonMain/.../di/AppModule.kt
val appModule = module {
    single<AppDatabase> { getRoomDatabase(getDatabaseBuilder()) }
    viewModelOf(::TemperatureViewModel)
    viewModelOf(::DistanceViewModel)
}

// In a composable (e.g. App.kt)
viewModel = koinViewModel<TemperatureViewModel>()
```

**Speaker Notes:**
The brain of the app is the converter ViewModels—we have TemperatureViewModel and DistanceViewModel, both in shared code and registered with Koin. When the UI loads, it asks for the ViewModel via koinViewModel(). It doesn't care if it's running on a Pixel or an iPhone. The ViewModels survive configuration changes on Android and manage state on iOS seamlessly. Same classes everywhere.

### Data Layer: Room & DataStore

* Room saves conversion history. DataStore saves the last selected unit. All in shared code.
* The only platform bit: a small expect/actual for where to put the DB file (e.g. Application Support on iOS, getDatabasePath on Android).
* No CoreData, no SharedPreferences. One persistence layer.

```kotlin
// commonMain/Platform.kt
expect fun getDatabaseBuilder(): RoomDatabase.Builder<AppDatabase>

// androidMain: path from context.getDatabasePath("...")
actual fun getDatabaseBuilder() = Room.databaseBuilder<AppDatabase>(
    context = context,
    name = context.getDatabasePath("CMPUnitConverter.db").absolutePath
)
// iosMain: path from getDirectoryForType(DirectoryType.Database) → Application Support
```

**Speaker Notes:**
For data, we use Room and DataStore.
The app saves your conversion history in a SQLite database using Room. It remembers your last selected unit using DataStore. The only platform-specific code here is a tiny `expect/actual` function to tell the app *where* to save the file on disk (e.g. Application Support on iOS, getDatabasePath on Android).
Everything else—the DAOs, the queries, the preference keys—is shared. We aren't fighting with CoreData or SharedPreferences.

### Platform Entry Points

* Android: Application calls initKoin at startup; MainActivity calls setContent. iOS: ComposeView inside a ViewController. Desktop: main() launches the window.
* One quirk: Kotlin/Native exports Unit-returning top-level functions with a "do" prefix in Swift. So you have initKoin() in Kotlin but call doInitKoin() from Swift. Don't "fix" it—that's the convention.

```kotlin
// commonMain/.../di/KoinApp.kt
fun initKoin(config: KoinAppDeclaration? = null) {
    startKoin {
        includes(config)
        modules(appModule)
    }
}
```

```swift
// iOS (Swift). Kotlin exports Unit-returning functions with "do" prefix:
KoinAppKt.doInitKoin()
```

**Speaker Notes:**
The platform apps are just entry points. On Android, the Application class calls initKoin at startup (e.g. in CMPUnitConverterApp); MainActivity then calls setContent. On iOS, ComposeView inside a ViewController. On Desktop, main() launches the window.
There is one little 'gotcha' I want to highlight on iOS. When we export our Kotlin `initKoin` function to Swift, Kotlin/Native renames it to `doInitKoin` because it returns `Unit`. It’s a small quirk, but knowing it saves you a 'Method Not Found' error. You call `doInitKoin` in your Swift AppLifecycle, and you're good to go.

### Version alignment

* Keep Compose Multiplatform, Compose Compiler plugin, and Kotlin Multiplatform plugin in sync. The docs have tables—use them.
* Your Android app's `activity-compose` must match the Compose version the shared module uses. If they're out of sync, you can get `NoSuchMethodError` at `setContent` and other runtime surprises.
* When you upgrade, check the compatibility page first. It saves a lot of debugging.

**Speaker Notes:**
One of the most common pitfalls is version mismatch. Your shared module pulls in Compose Multiplatform and a specific Compose runtime. Your Android app uses `activity-compose` to call `setContent`. If `activity-compose` was built for a different Compose version, you get crashes like `NoSuchMethodError` at runtime. So align `activity-compose` with the Jetpack Compose version that Compose Multiplatform uses—there's a compatibility table in the docs. Also keep the Compose Compiler plugin version in sync with your Kotlin Multiplatform plugin. When in doubt, check the Compose Multiplatform and AGP compatibility pages before you upgrade. That habit pays off.

### Lessons from the trenches

* Align versions early. Check the compatibility tables before you upgrade—it saves pain later.
* If you're starting a new project, use the AGP 9 structure from day one. Migrating later works but is more work.
* Put platform quirks behind expect/actual and document them (like doInitKoin). Keeps shared code clean and stops people "fixing" what isn't broken.

**Speaker Notes:**
A few other things help in practice. If you're starting fresh, use the AGP 9 structure from day one. Migrating an old single-module app to the new structure works, but it's more work. And expect/actual is your friend: put every platform quirk behind an expect declaration, implement it per platform, and document the odd ones—like doInitKoin on iOS—so your future self or your team don't "fix" what isn't broken. That keeps the shared code clean and the architecture solid.

### Time to wrap things up: what I showed you

* The story: Jetpack history, the Jungle, the Golden Path, and the twist—same stack, beyond Android.
* The lineup: Compose Multiplatform, ViewModel, Navigation, Room, DataStore. All in KMP.
* How it fits: one shared module, thin platform apps, entry points, DI. AGP 9 structure in practice.
* The case study: CMP Unit Converter—one codebase, Android, iOS, Desktop. Clone it and try it.

**Speaker Notes:**
So, what did we cover? The story—where Jetpack came from, the jungle of 130+ artifacts, the Golden Path map, and the twist that it all runs beyond Android. The lineup: Compose Multiplatform, ViewModel, Navigation, Room, DataStore, all available in KMP. How it fits together: one shared module, thin platform shells, entry points, dependency injection. And we looked at the AGP 9 structure in a real project, CMP Unit Converter—one codebase, multiple binaries. That's what I showed you. Next, what I didn't.

### What I left out

* **Testing.** You can unit test ViewModels, repositories, and use cases in commonTest (or JVM). Room: in-memory DB, same DAOs. expect/actual: test doubles. The docs and the sample repo have the details.
* **Version alignment.** We had a slide on it—`activity-compose` and Compose versions must match. When you upgrade, check the compatibility tables. It saves a lot of pain.
* **What's not (yet) in KMP.** WorkManager, CameraX, and some others are still Android-only. For those you keep them in the app module or find alternatives. The Golden Path stack we used is the solid core.

**Speaker Notes:**
I didn't cover everything. Testing: your shared module is highly testable. ViewModels, repositories, use cases—you can run them in commonTest or on the JVM. Room works with an in-memory database; same DAOs and entities. For expect/actual you supply test implementations. The Kotlin and Android docs and the CMP Unit Converter repo show how. Version alignment: we touched it in the checklist. When you upgrade, align `activity-compose` with the Compose version your shared module uses, and check the compatibility pages. And what's not in KMP yet: WorkManager, CameraX, a few others. For those you stay in the Android app module or look for community options. The stack we focused on is the part that's officially supported and where you'll spend most of your time.

### Jetpack roadmap beyond Android

* Google and JetBrains are unbundling Jetpack from the Android platform. It's happening.
* More libraries will get KMP support. The Golden Path stack is already at the front.
* One architecture, many platforms—that's the direction. Investing in this stack isn't a bet on a niche.

**Speaker Notes:**
Where is this going? Google and JetBrains are actively moving Jetpack beyond Android. We're not there yet for every library, but the trend is clear: the same APIs you use on Android are being made available in KMP. The Golden Path stack we've talked about is at the front of that wave. So investing in this architecture now—Compose, ViewModel, Navigation, Room, DataStore—is not a bet on a niche. It's aligning with where the ecosystem is headed. One architecture, many platforms.

### A checklist for your projects

* UI: Compose Multiplatform. State: ViewModel + Lifecycle Runtime in KMP.
* Navigation: Navigation Compose (KMP). Persistence: Room + DataStore (KMP).
* Structure: one shared module, thin Android / iOS / Desktop apps. On Android, use the AGP 9 layout.
* DI: Koin or Hilt—init once per platform. Stick to this stack and you avoid the jungle.

**Speaker Notes:**
So when you go back to your desk, here's the checklist. UI: Compose Multiplatform. State: ViewModel and Lifecycle Runtime in KMP. Navigation: Navigation Compose in KMP. Persistence: Room and DataStore in KMP. Structure: one shared module, thin Android, iOS, and Desktop apps—and if you're on Android, use the AGP 9 layout with the new KMP library plugin. Dependency injection: Koin or Hilt, init once at app startup on each platform. Stick to this stack and you avoid the jungle. You get one architecture that runs everywhere.

### Resources

* Kotlin Multiplatform & Compose Multiplatform: kotlinlang.org, jetbrains.com.
* Android KMP library plugin & AGP 9 migration: developer.android.com.
* Compose Multiplatform compatibility & version tables—check before you upgrade.
* Sample: CMP Unit Converter and other KMP samples on GitHub.

**Speaker Notes:**
For more detail, the Kotlin and Compose Multiplatform docs on kotlinlang.org and jetbrains.com are the place to start. For the Android side—the new KMP library plugin, AGP 9, and the migration steps—developer.android.com has the official guides. The Compose Multiplatform compatibility page tells you which Jetpack Compose version lines up with which CMP version. When in doubt, check it before you upgrade—it saves head-scratching. And there are samples: CMP Unit Converter is one; the Kotlin and Android teams publish more. You're not on your own—the docs and the samples are there to back you up.
