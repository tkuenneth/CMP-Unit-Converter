# Refuelling your Jetpack

### The "Before Times" (Android Support Library)

- The problem of fragmentation: new features tied to slow OS updates.
- The band-aid: the Android Support Library (android.support.*).
- The breaking point: monolithic libraries (remember support-v4?), versioning nightmares, and "Dependency Hell."

---
*Speaker notes:* To understand where Jetpack is going, we have to remember where we started. In the early days of Android, if you wanted to use a new API, your users needed the newest OS version—which we all know took forever. Google's solution was the Android Support Library. It was a lifesaver, bringing new features to older devices. But over time, it became a victim of its own success. The android.support namespace turned into a massive, tangled web. We had monolithic dependencies like support-v4 where you had to import the whole world just to get one tiny utility. Versioning was a nightmare, and resolving dependency conflicts was a daily struggle. We needed a clean slate.

### The Reboot – Enter Android Jetpack

- Google I/O 2018: the great reboot to androidx.*.
- Strictly unbundled: independent libraries, independent update cycles.
- Semantic versioning: clear, predictable compatibility.
- Opinionated architecture: moving from "do it however you want" to "here is the best way to do it."

---
*Speaker notes:* In 2018, Google hit the reset button and introduced Android Jetpack. This wasn't just a simple rebranding; it was a fundamental architectural shift. They migrated everything to the new androidx.* namespace. More importantly, Jetpack modularized the ecosystem. Libraries were finally unbundled, meaning they had their own release cycles and semantic versioning. You could update your RecyclerView without breaking your ViewModel. But Jetpack also represented a philosophical shift: Google finally started giving us opinionated guidance on app architecture. It gave us a standardized toolkit to reduce boilerplate and write robust apps. And as we'll see today, that toolkit is no longer confined just to Android.

### The Jetpack Jungle ("Why are we here?")

- The 130+ artifact reality: it's not just hyper-modularity; it's also historical baggage.
- The "zombie" libraries: deprecated artifacts, duplicate functionalities, and legacy APIs that still haunt the documentation.
- The paradox of choice: how do you build a modern architecture when there are three different ways to do the exact same thing?

---
*Speaker notes:* Let's be brutally honest for a second. The idea of Jetpack sounded perfect in 2018, but 8 years later, we are staring down the barrel of a new kind of mess. If you look at the official AndroidX registry today, there are over 130 libraries. And no, they aren't all pristine, regularly updated tools. We have obsolete zombie libraries like lifecycle-extensions or security-crypto that Google abandoned long ago but still haunt the documentation. We have duplicate functionalities where you have to choose between DataBinding, ViewBinding, or Compose just to handle UI state. We went from 'Dependency Hell' straight into the 'Paradox of Choice.' It is incredibly easy to drown in this flood of artifacts. How are you supposed to define a robust, modern app architecture when it feels like you need a machete just to figure out which libraries are actually still recommended? You need a map to cut through the noise.

### The Map (The "Golden Path")

- Clean architecture: filtering out the noise to find the core pillars.
- Unidirectional data flow: UI reacts to state, events flow up.
- State management: ViewModels surviving configuration changes.
- Navigation: moving between screens with a single source of truth.
- Robust persistence: reactive local databases and typed preferences (Room, DataStore).

---
*Speaker notes:* So, if we take our machete, cut away the zombie libraries, the legacy code, and the duplicate frameworks, what are we left with? We find this map. This is the 'Golden Path' for modern app development. It's clean, it's opinionated, and it relies on a very specific, curated stack: We have a declarative UI reacting to state. We have ViewModels managing that state and surviving lifecycle changes. We use Navigation to move between screens. And down in the data layer, we have robust, reactive persistence using Room for our databases and DataStore for our preferences. If you stick to exactly these tools, you avoid the jungle entirely and end up with a beautifully architected app.

### The Twist

- The big reveal: this isn't just an Android stack anymore.
- Compose Multiplatform (CMP): the UI layer, everywhere.
- Kotlin Multiplatform (KMP): the ViewModels, DataStore, and Room… running on iOS.
- The real power of Jetpack: write your architecture once, deploy it anywhere.

---
*Speaker notes:* Now, you are all sitting there looking at this map, probably thinking, 'Sure, that's a great, modern Android architecture.' But here is the twist: I'm not talking about Android. Everything you just looked at on that map—Compose, ViewModel, Navigation, Room, DataStore—is now fully functional in Compose Multiplatform and Kotlin Multiplatform. You can take that exact same Jetpack architecture, those exact same APIs that you already know, and compile them natively for iOS, Desktop, and the Web. Google didn't just unbundle Jetpack from the Android OS; they are actively unbundling it from the Android platform entirely.

### Jetpack on KMP: the lineup

- The Golden Path stack is available beyond Android.
- UI: Compose Multiplatform (CMP).
- State & lifecycle: ViewModel, Lifecycle Runtime (KMP).
- Navigation: Navigation Compose (KMP).
- Persistence: Room, DataStore (KMP).

---
*Speaker notes:* So which pieces of Jetpack are actually available in Kotlin Multiplatform today? The good news is: the same curated stack from our map. Compose Multiplatform gives you the UI layer on Android, iOS, Desktop, and Web. Under the hood, ViewModel and Lifecycle are available in shared code via the KMP versions from JetBrains and Google. Navigation Compose works across platforms. Room and DataStore have official KMP support. So you're not betting on a handful of random libraries—you're using the same pillars you'd use on Android, with the same mental model.

### UI: Compose Multiplatform

- One UI toolkit: Compose, from Jetpack Compose to CMP.
- Declarative, reactive: same concepts on every platform.
- Android, iOS, Desktop, Web: one codebase, native look and feel.

---
*Speaker notes:* The UI layer is Compose Multiplatform. It's the same declarative, reactive paradigm as Jetpack Compose. You write composables, they react to state, and you get a consistent development experience. On Android you get Material and the full ecosystem; on iOS and Desktop you get the same API with native rendering. So your screen logic, your navigation structure, and your state-driven UI can all live in shared code. If you already know Compose on Android, you're most of the way there.

### State & lifecycle: ViewModel & Runtime

- ViewModels in shared code: survive configuration and process.
- Lifecycle Runtime Compose: lifecycle-aware composables.
- Same API you know from Android; runs on all targets.

---
*Speaker notes:* ViewModels aren't an Android-only concept anymore in this stack. The lifecycle and viewmodel libraries have KMP artifacts. You define your ViewModels in commonMain, and they work on Android, iOS, and Desktop. You get the same benefits: surviving configuration changes, clear ownership of state, and a single place for screen logic. Lifecycle Runtime Compose gives you lifecycle-aware composition so your UI can react to lifecycle events in a multiplatform way. It's the same mental model, just unbundled from the Android platform.

### Navigation

- Navigation Compose: one graph, multiple platforms.
- Type-safe routes and back stack in shared code.
- Same navigation API on Android, iOS, and Desktop.

---
*Speaker notes:* Navigation is another piece that travels with you. Navigation Compose has a KMP version, so you declare your graph and your routes in shared code. Screen A, B, C—same graph on every platform. Type-safe routing, back stack, and deep links can all be shared. Your platform entry points—the Android Activity, the iOS view controller, the Desktop window—just host the same composable navigation host. So you're not reimplementing navigation per platform.

### Persistence: Room

- Room in KMP: SQLite, DAOs, reactive Flow queries.
- Same annotations, same patterns; runs on Android, iOS, Desktop.
- One database layer for your shared business logic.

---
*Speaker notes:* Room is the persistence layer you already use on Android. In KMP, Room works in commonMain with the same annotations—Entity, Dao, Database. You get type-safe queries, Flow for reactive results, and the same migration and testing story. Under the hood it uses SQLite on each platform. So your data layer—tables, DAOs, repositories—can all live in shared code. One schema, one API, and your ViewModels consume it the same way on every platform.

### Preferences: DataStore

- DataStore in KMP: typed preferences, async, no SharedPreferences.
- Key-value or protocol buffers; same API across platforms.
- Settings and lightweight state, shared.

---
*Speaker notes:* For preferences and lightweight configuration, DataStore is the modern replacement for SharedPreferences—and it's available in KMP. You get typed preferences, async APIs, and no more blocking on the main thread. Whether you use the simple key-value API or protocol buffers, the same code runs on Android, iOS, and Desktop. So your app settings, feature flags, or user preferences can live in shared code and be read and written the same way everywhere. That's the full lineup: UI, state, navigation, database, and preferences—all on the Golden Path, all multiplatform.

### One architecture, three (or more) platforms

- One shared module: UI, ViewModels, navigation, Room, DataStore.
- Thin platform shells: Android app, iOS app, Desktop app (and Web).
- Same logic, same screens; each platform just hosts and builds.

---
*Speaker notes:* So how does this actually fit together? You have one shared module that contains almost everything: your Compose UI, your ViewModels, your navigation graph, your Room database and DataStore. Then you have thin platform modules—an Android app, an iOS app, a Desktop app—that do little more than start the process and host the shared UI. They might add a splash screen, a menu bar on Desktop, or platform-specific theming, but the core of your app is shared. One architecture, compiled and run on each platform. No duplicate business logic, no sync issues between platforms.

### Shared module: the heart

- commonMain: Compose UI, ViewModels, Navigation, Room, DataStore.
- expect/actual: platform APIs (e.g. files, preferences paths, back handling).
- One source of truth for behaviour and data.

---
*Speaker notes:* The shared module is the heart. In commonMain you have your composables, your ViewModels, your navigation, your DAOs and repositories, and your DataStore usage. When you need something platform-specific—like "where do I store the database file?" or "how do I open a URL?"—you use expect/actual. You declare the API in commonMain and implement it in androidMain, iosMain, and desktopMain. So the shared code stays clean and testable, and platform details stay at the edges. One source of truth for how your app behaves and what data it uses.

### Platform entry points

- Android: Activity + setContent; iOS: UIViewController hosting Compose; Desktop: main() + Window.
- Each platform wires lifecycle and hosts the same root composable.
- Koin or DI init at startup; then the shared app runs.

---
*Speaker notes:* The platform modules are thin. On Android you have an Activity that calls setContent and shows your root composable—maybe your NavHost. On iOS you have a view controller that hosts the Compose UI. On Desktop you have a main function that opens a window and runs the same composable. Each one initializes dependency injection—for example Koin—at startup, and then the rest is shared. So the entry point is the only place where you're writing platform-specific code; after that, it's the same app.

### Dependency injection

- Koin (or Hilt): define modules in shared code; inject in ViewModels and composables.
- Platform-specific modules: e.g. Android Context, iOS/Desktop equivalents.
- One DI container; actual implementations can be platform-specific where needed.

---
*Speaker notes:* Dependency injection fits naturally. You define your modules in shared code—your repositories, your use cases, your ViewModels. You might have a platform module that provides things like "where is the app's data directory?" or "how do I get a Context on Android?" So the shared module depends on interfaces or expect declarations, and each platform supplies the actual. When you launch the app, you init Koin or Hilt once, and the whole shared tree gets its dependencies. Same architecture, same injection pattern, on every platform.

### From map to code

- Shared module: UI + ViewModels + Navigation + Room + DataStore.
- Platform modules: Gradle (Android, Desktop) + Xcode (iOS); minimal code.
- The Golden Path in practice: one stack, one codebase, multiple binaries.

---
*Speaker notes:* So when you open a real project, you see this structure. The shared module has your screens, your ViewModels, your navigation graph, your Room database and DataStore. The Android and Desktop apps are Gradle modules that depend on shared; the iOS app is an Xcode project that links the shared framework. Each platform build produces its own binary, but they all run the same logic. That's the Golden Path in practice: one stack, one codebase, multiple binaries. Next we'll look at a few practical details that will save you time when you try this yourself.

### AGP 9 & the new project structure

- Separate Android app module: no KMP in the app; shared is a library.
- New plugin: `com.android.kotlin.multiplatform.library` in shared; `androidLibrary { }` instead of `android { }`.
- Built-in Kotlin in the app module; Gradle 9.1, AGP 9.

---
*Speaker notes:* If you're starting a new project or migrating an existing one, the recommended structure has changed with AGP 9. Your Android app should be its own module—just application code, no Kotlin Multiplatform plugin. The shared module uses the new Android KMP library plugin, and you configure the Android target inside the Kotlin block with androidLibrary, not the old top-level android block. The app module uses AGP's built-in Kotlin, so you don't apply the Kotlin Android plugin there. Gradle 9.1 and AGP 9 are the baseline. It's a bit of a mental shift if you had one composeApp doing everything, but it pays off in clearer separation and faster builds.

### Version alignment matters

- Compose Multiplatform, Compose Compiler, Kotlin: use compatible versions.
- activity-compose: align with the Compose version your shared module uses (e.g. 1.10.x).
- Mismatches cause runtime errors (e.g. setContent NoSuchMethodError); check the compatibility table.

---
*Speaker notes:* One of the most common pitfalls is version mismatch. Your shared module pulls in Compose Multiplatform and a specific Compose runtime. Your Android app uses activity-compose to call setContent. If activity-compose was built for a different Compose version, you get crashes like NoSuchMethodError at runtime. So align activity-compose with the Jetpack Compose version that Compose Multiplatform uses—there's a compatibility table in the docs. Also keep the Compose Compiler version in sync with your Kotlin version. When in doubt, check the Compose Multiplatform and AGP compatibility pages. It saves a lot of head-scratching.

### iOS: one quirk (doInitKoin)

- Kotlin/Native exports Unit-returning top-level functions with a "do" prefix in Swift.
- Your Kotlin: `fun initKoin()`; from Swift you call `KoinAppKt.doInitKoin()`.
- Same function; the export naming is automatic. Don't "fix" it to initKoin in Swift.

---
*Speaker notes:* Here's a fun one. When you export your shared module to iOS, top-level Kotlin functions that return Unit get a "do" prefix in the generated Swift API. So if you have initKoin in KoinApp.kt, Swift sees doInitKoin. If you try to call initKoin from Swift, it won't compile—there is no such member. The Koin docs and samples use doInitKoin for exactly this reason. So when you see it, it's not a typo; it's the Kotlin/Native export convention. Stick with doInitKoin and you're good.

### Testing the shared module

- Unit test ViewModels, repositories, and use cases in commonTest (or platform test).
- Room: in-memory or test DB; same DAOs and entities.
- expect/actual: use test doubles or platform-specific test implementations.

---
*Speaker notes:* Your shared module is highly testable. ViewModels, repositories, and use cases don't depend on the UI framework at runtime—you can test them with Kotlin Test or JUnit in commonTest or in a JVM test source set. For Room, use an in-memory database or a test driver; the same DAOs and entities work. For expect/actual, you can supply test implementations in test source sets or use mocks. So you're not stuck testing only on a device; a lot of your logic can be covered with fast, local unit tests.

### Resources and assets

- Compose Multiplatform resources: drawables, strings, etc. in shared/composeResources.
- One set of assets; referenced the same way from commonMain.
- Platform-specific assets (e.g. app icons) stay in each app module.

---
*Speaker notes:* For images, strings, and other resources, Compose Multiplatform has a resources system. You put them in the shared module under composeResources—drawables, strings, whatever you need. You reference them from commonMain with the generated Res API, and they're available on every platform. So your shared UI can use the same icons and strings everywhere. Platform-specific assets, like the app launcher icon or splash screen, stay in each app module. That keeps the shared module truly shared and avoids platform ifs in your resource loading.

### What's not (yet) in KMP

- Some Jetpack libraries are still Android-only (e.g. WorkManager, CameraX).
- Others have partial or community KMP support; check the docs and issue trackers.
- The Golden Path stack (Compose, ViewModel, Navigation, Room, DataStore) is the solid core.

---
*Speaker notes:* Let's be honest: not every Jetpack library is in KMP today. WorkManager, CameraX, and some others are still Android-only. For those you either keep them in the Android app module or look for community alternatives. The good news is that the Golden Path stack we've been talking about—Compose, ViewModel, Navigation, Room, DataStore—is the part that's officially supported and widely used. So you can build a large class of apps with this stack and only reach for platform-specific code when you hit something that isn't ported yet. As the ecosystem evolves, more will follow.

### Example: CMP Unit Converter

- A real app: unit conversion (temperature, distance, etc.) on Android, iOS, and Desktop.
- Built on the Golden Path: Compose, ViewModel, Navigation, Room, DataStore, Koin.
- Shared module + thin platform apps; AGP 9, Gradle 9.1.

---
*Speaker notes:* I've been working with a sample app called CMP Unit Converter—unit conversion for temperature, distance, and so on. It runs on Android, iOS, and Desktop from one codebase. It uses exactly the stack we've been talking about: Compose Multiplatform for UI, ViewModel and Lifecycle for state, Navigation Compose, Room for history, DataStore for preferences, and Koin for dependency injection. The structure is what we described: a shared module with all the logic and UI, and thin platform modules. It's built with AGP 9 and the new project structure. So everything in this talk is something you can see and run in that project.

### Shared logic in action

- One screen: state from ViewModel, persistence from Room, preferences from DataStore.
- Navigation between screens; same graph on every platform.
- No platform ifs in the business logic—only at the edges (e.g. file paths, back handling).

---
*Speaker notes:* When you open a screen in the app, the flow is the same everywhere. The composable gets state from a ViewModel. The ViewModel might read from a Room repository or DataStore. Navigation moves you to the next screen with the same back stack semantics. All of that lives in shared code. The only platform-specific pieces are things like "where does the database file go?" or "how do we handle the back button on Android vs iOS?"—and those are behind expect/actual. So when you're writing or debugging the main flow, you're in one place. That's the payoff of the architecture.

### Same UI, three platforms

- Same screens, same behaviour; each platform compiles and runs natively.
- Android: Material and system integration; iOS: Compose in a view controller; Desktop: window and menu bar.
- One design, one codebase; platform polish at the entry point only.

---
*Speaker notes:* The UI is the same Compose code on all three platforms. You get the same screens, the same navigation, the same state. On Android it's Material and fits the system. On iOS the Compose UI runs inside a view controller. On Desktop it's a window with an optional menu bar. So one design, one codebase—and you only add platform polish at the edges, like the app icon, the launcher, or the menu bar. You're not maintaining three separate UIs.

### Lessons from the trenches

- Align versions early; check the compatibility tables before you upgrade.
- Use the new AGP 9 structure from the start; migration later is harder.
- Expect/actual keeps shared code clean; put platform quirks there and document them (e.g. doInitKoin).

---
*Speaker notes:* A few takeaways from building and migrating this. First, version alignment really matters—Compose, Kotlin, activity-compose. Check the docs and compatibility tables before you upgrade; it saves a lot of pain. Second, if you're starting fresh, use the AGP 9 structure from day one. Migrating an old single-module app to the new structure works, but it's more work. Third, expect/actual is your friend. Put every platform quirk behind an expect declaration, implement it per platform, and document the odd ones—like doInitKoin on iOS—so your future self or your team don't "fix" what isn't broken. That's how you keep the shared code clean and the architecture solid.

### The Jetpack roadmap beyond Android

- Google and JetBrains: Jetpack libraries unbundling from the Android platform.
- More KMP support over time; the Golden Path is the vanguard.
- One architecture, many platforms—the direction of travel is clear.

---
*Speaker notes:* Where is this going? Google and JetBrains are actively moving Jetpack beyond Android. We're not there yet for every library, but the trend is clear: the same APIs you use on Android are being made available in KMP. The Golden Path stack we've talked about is at the front of that wave. So investing in this architecture now—Compose, ViewModel, Navigation, Room, DataStore—is not a bet on a niche. It's aligning with where the ecosystem is headed. One architecture, many platforms.

### Your Golden Path checklist

- UI: Compose Multiplatform.
- State: ViewModel + Lifecycle Runtime (KMP).
- Navigation: Navigation Compose (KMP).
- Persistence: Room + DataStore (KMP).
- Structure: shared module + thin platform apps; AGP 9 for Android.
- DI: Koin or Hilt; init once per platform.

---
*Speaker notes:* So when you go back to your desk, here's the checklist. UI: Compose Multiplatform. State: ViewModel and Lifecycle Runtime in KMP. Navigation: Navigation Compose in KMP. Persistence: Room and DataStore in KMP. Structure: one shared module, thin Android, iOS, and Desktop apps—and if you're on Android, use the AGP 9 layout with the new KMP library plugin. Dependency injection: Koin or Hilt, init once at app startup on each platform. Stick to this stack and you avoid the jungle. You get one architecture that runs everywhere.

### Resources & further reading

- Kotlin Multiplatform & Compose Multiplatform docs (kotlinlang.org, jetbrains.com).
- Android KMP library plugin & AGP 9 migration (developer.android.com).
- Compose Multiplatform compatibility & version tables.
- Sample: CMP Unit Converter (and other KMP samples on GitHub).

---
*Speaker notes:* For more detail, the Kotlin and Compose Multiplatform docs on kotlinlang.org and jetbrains.com are the place to start. For the Android side—the new KMP library plugin, AGP 9, and the migration steps—developer.android.com has the official guides. The Compose Multiplatform compatibility page tells you which Jetpack Compose version lines up with which CMP version. And there are samples: CMP Unit Converter is one; the Kotlin and Android teams publish more. So you're not on your own—the docs and the samples are there to back you up.

### Thank you / Q&A

- Refuelling your Jetpack: the same stack, beyond Android.
- Questions?

---
*Speaker notes:* So that's Refuelling your Jetpack. We went from the Support Library and Dependency Hell to Jetpack, then to the jungle of 130+ artifacts, then to the map—the Golden Path—and the twist that it's not just Android anymore. We looked at which libraries are in KMP, how they fit together, and a few practical tips. I hope you leave with a clear picture of how to build one architecture and run it everywhere. Thank you—and I'm happy to take your questions.
