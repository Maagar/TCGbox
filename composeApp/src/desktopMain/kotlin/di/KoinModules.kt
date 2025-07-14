package di

import presentation.screen.addCards.AddCardsViewModel
import presentation.screen.userCards.UserCardsViewModel
import data.api.KtorClient
import data.api.PokemonApiService
import data.db.DriverFactory
import data.db.createDatabase
import data.repository.PokemonRepository
import org.koin.core.context.startKoin
import org.koin.core.logger.PrintLogger
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import presentation.screen.settings.SettingsViewModel

val appModule = module {
    //HTTP
    single { KtorClient.httpClient }
    single { PokemonApiService(get()) }
    single { PokemonRepository(get(), get()) }

    //SqlDelight
    single { DriverFactory().createDriver() }
    single { createDatabase(get()) }

    //ViewModels
    viewModel { UserCardsViewModel(get()) }
    viewModel { AddCardsViewModel(get()) }
    viewModel { SettingsViewModel(get()) }
}

fun initializeKoin() {
    startKoin {
        modules(appModule)
        logger(PrintLogger())
    }
}