package de.thomaskuenneth.cmpunitconverter.di

import de.thomaskuenneth.cmpunitconverter.distance.DistanceRepository
import de.thomaskuenneth.cmpunitconverter.distance.DistanceViewModel
import de.thomaskuenneth.cmpunitconverter.temperature.TemperatureRepository
import de.thomaskuenneth.cmpunitconverter.temperature.TemperatureViewModel
import de.thomaskuenneth.cmpunitconverter.app.AppRepository
import de.thomaskuenneth.cmpunitconverter.app.AppViewModel
import de.thomaskuenneth.cmpunitconverter.TemperatureSupportingPaneUseCase
import de.thomaskuenneth.cmpunitconverter.DistanceSupportingPaneUseCase
import de.thomaskuenneth.cmpunitconverter.HistoryRepository
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val appModule = module {
    singleOf(::HistoryRepository)

    singleOf(::AppRepository)
    viewModelOf(::AppViewModel)

    singleOf(::TemperatureRepository)
    singleOf(::TemperatureSupportingPaneUseCase)
    viewModelOf(::TemperatureViewModel)

    singleOf(::DistanceRepository)
    singleOf(::DistanceSupportingPaneUseCase)
    viewModelOf(::DistanceViewModel)
}
