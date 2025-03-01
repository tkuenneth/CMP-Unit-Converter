package de.thomaskuenneth.cmpunitconverter

import de.thomaskuenneth.cmpunitconverter.distance.DistanceRepository
import de.thomaskuenneth.cmpunitconverter.distance.DistanceViewModel
import de.thomaskuenneth.cmpunitconverter.temperature.TemperatureRepository
import de.thomaskuenneth.cmpunitconverter.temperature.TemperatureViewModel
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val appModule = module {
    singleOf(::TemperatureRepository)
    singleOf(::DistanceRepository)
    viewModelOf(::TemperatureViewModel)
    viewModelOf(::DistanceViewModel)
}
