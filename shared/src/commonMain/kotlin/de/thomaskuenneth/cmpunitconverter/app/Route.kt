package de.thomaskuenneth.cmpunitconverter.app

import androidx.navigation3.runtime.NavKey
import androidx.savedstate.serialization.SavedStateConfiguration
import kotlinx.serialization.Serializable
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import kotlinx.serialization.serializer

@Serializable
sealed interface Route : NavKey

@Serializable
data object TemperatureRoute : Route

@Serializable
data object DistanceRoute : Route

// Serialization configuration for Navigation 3 back stack on all platforms
val routeSavedStateConfiguration: SavedStateConfiguration = SavedStateConfiguration {
    serializersModule = SerializersModule {
        polymorphic(NavKey::class) {
            subclass(TemperatureRoute::class, serializer<TemperatureRoute>())
            subclass(DistanceRoute::class, serializer<DistanceRoute>())
        }
    }
}


