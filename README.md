# Welcome to CMP Unit Converter

This repo contains a real-world example of a *Compose Multiplatform* app. *CMP Unit Converter* runs on Android, iOS, and the Desktop. As its name suggests, you can convert between various units and scales. While this may provide some value, the main goal of the app and the accompanying series on [dev.to](https://dev.to/tkuenneth/series/30921) is to show how to use Compose Multiplatform and a couple of other multiplatform libraries while focusing on platform integration.

Among others, these libraries are used:

- [Koin](https://insert-koin.io/)
- [Compose Material 3 Adaptive](https://developer.android.com/jetpack/androidx/releases/compose-material3-adaptive)
- [DataStore](https://developer.android.com/kotlin/multiplatform/datastore)
- [Navigation Compose](https://www.jetbrains.com/help/kotlin-multiplatform-dev/compose-navigation-routing.html)
- [Room](https://developer.android.com/kotlin/multiplatform/room)

Unlike many other samples, this one has a strong focus on platform integration. For example, on the Desktop, you can access the settings, the *About* dialog, and all top-level destinations from the menu bar. On Android and iOS, a top app bar is used. The app supports dark and light mode on all platforms. Database and configuration files are stored in the preferred locations.

### A note about icons

The [official Android Compose documentation on Material icons](https://developer.android.com/develop/ui/compose/graphics/images/material) recommends against adding *material-icons-extended* as a direct dependency (it is very large and can significantly increase build time) and instead suggests copying only the icons you need or using [Google Font Icons](https://fonts.google.com/icons) (e.g. as SVG or Android vector drawable). This app follows that approach: the icons in use are stored as vector drawables in `shared/src/commonMain/composeResources/drawable/` and referenced via `AppIcons`. Those icon assets are from the [Material Design Icons](https://github.com/google/material-design-icons) project (Apache License 2.0).

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
