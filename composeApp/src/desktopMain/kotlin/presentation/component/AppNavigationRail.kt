package presentation.component

import presentation.navigation.AppRoutes
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import org.jetbrains.compose.resources.painterResource
import tcgbox.composeapp.generated.resources.Res
import tcgbox.composeapp.generated.resources.home
import tcgbox.composeapp.generated.resources.playing_cards
import tcgbox.composeapp.generated.resources.settings

@Composable
fun AppNavigationRail(
    navController: NavController,
    currentRoute: String?
) {
    NavigationRail(modifier = Modifier.fillMaxHeight().width(80.dp)) {
        Spacer(Modifier.weight(1f))

        NavigationRailItem(
            selected = currentRoute == AppRoutes.USER_CARDS,
            onClick = {
                if (currentRoute != AppRoutes.USER_CARDS) {
                    navController.navigate(AppRoutes.USER_CARDS) {
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            },
            icon = { Icon(painter = painterResource(Res.drawable.home), contentDescription = null) },
            label = { Text("Moje karty") },
        )

        NavigationRailItem(
            selected = currentRoute == AppRoutes.ADD_CARDS,
            onClick = {
                if (currentRoute != AppRoutes.ADD_CARDS) {
                    navController.navigate(AppRoutes.ADD_CARDS) {
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            },
            icon = { Icon(painter = painterResource(Res.drawable.playing_cards), contentDescription = null) },
            label = { Text("Dodaj karty") },
        )

        NavigationRailItem(
            selected = currentRoute == AppRoutes.SETTINGS,
            onClick = {
                if (currentRoute != AppRoutes.SETTINGS) {
                    navController.navigate(AppRoutes.SETTINGS) {
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            },
            icon = { Icon(painter = painterResource(Res.drawable.settings), contentDescription = null) },
            label = { Text("Ustawienia") },
        )
        Spacer(Modifier.weight(1f))
    }
}