# Welcome to CMP Unit Converter

This repo contains a real-world example of a *Compose Multiplatform* app. *CMP Unit Converter* runs on Android, iOS, and the Desktop. As its name suggests, you can convert between various units. While this may provide some value, the main goal is to show how to use Compose Multiplatform and a couple of other multiplatform libraries, for example:

- [Koin](https://insert-koin.io/)
- [Compose Material 3 Adaptive](https://developer.android.com/jetpack/androidx/releases/compose-material3-adaptive)
- [DataStore](https://developer.android.com/kotlin/multiplatform/datastore)

Things worth noting:

- Strong focus on platform integration: on the Desktop, there's menu bar support including access to settings
- Navigate to top-level destinations using the menu bar (including keyboard shortcuts)
- Support for dark and light mode

### What's up next?

- Regularly updating dependencies

### Note to myself ;-)

The project contains a few workarounds to make the build smooth across platforms and targets. We should occasionally check if the workarounds are still necessary.

- Fix `java.lang.NoClassDefFoundError: sun/misc/Unsafe` by adding

```
modules("jdk.unsupported")
modules("jdk.unsupported.desktop")
```

- Fix `could not resolve all files for configuration ':kotlinNativeBundleConfiguration'` on Linux on ARM by adding

```
if (!System.getProperty("os.name").lowercase().contains("linux"))
```

- Refine *rules.pro*

While this preliminary version seems to work, it needs some more love for sure
