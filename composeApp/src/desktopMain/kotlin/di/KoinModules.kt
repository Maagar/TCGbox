package di

import Presentation.screen.addCards.AddCardsViewModel
import Presentation.screen.userCards.UserCardsViewModel
import data.api.KtorClient
import data.api.PokemonApiService
import data.repository.PokemonRepository
import org.koin.core.context.startKoin
import org.koin.core.logger.PrintLogger
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single { KtorClient.httpClient }
    single { PokemonApiService(get()) }
    single { PokemonRepository(get()) }
    viewModel { UserCardsViewModel(get()) }
    viewModel { AddCardsViewModel(get()) }
}

fun initializeKoin() {
    startKoin {
        modules(appModule)
        logger(PrintLogger())
    }
}