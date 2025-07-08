package Presentation.navigation

import Presentation.screen.addCards.AddCardsScreen
import Presentation.screen.userCards.UserCardsScreen
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun AppNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    NavHost(navController = navController, startDestination = AppRoutes.USER_CARDS) {
        composable(AppRoutes.USER_CARDS) { UserCardsScreen() }
        composable(AppRoutes.ADD_CARDS) { AddCardsScreen() }
    }
}