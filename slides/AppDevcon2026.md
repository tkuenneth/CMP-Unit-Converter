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
