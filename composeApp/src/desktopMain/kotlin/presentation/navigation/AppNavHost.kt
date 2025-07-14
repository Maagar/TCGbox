package presentation.navigation

import presentation.screen.addCards.AddCardsScreen
import presentation.screen.userCards.UserCardsScreen
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import presentation.screen.settings.SettingsScreen

@Composable
fun AppNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    NavHost(navController = navController, startDestination = AppRoutes.USER_CARDS) {
        composable(AppRoutes.USER_CARDS) { UserCardsScreen() }
        composable(AppRoutes.ADD_CARDS) { AddCardsScreen() }
        composable(AppRoutes.SETTINGS) { SettingsScreen() }
    }
}