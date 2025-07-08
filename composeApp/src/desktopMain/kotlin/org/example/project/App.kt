package org.example.project

import Presentation.navigation.AppNavHost
import Presentation.navigation.AppRoutes
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.compose.AppTheme
import org.koin.compose.KoinContext
import tcgbox.composeapp.generated.resources.Res
import tcgbox.composeapp.generated.resources.home
import org.jetbrains.compose.resources.painterResource
import tcgbox.composeapp.generated.resources.playing_cards

@Composable
fun App(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
) {
    KoinContext {
        AppTheme(darkTheme = darkTheme, dynamicColor = dynamicColor) {
            Surface(modifier = Modifier.fillMaxSize()) {

                val navController = rememberNavController()
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination?.route

                Row(modifier = Modifier.fillMaxSize()) {

                    NavigationRail(modifier = Modifier.fillMaxHeight().width(80.dp), ) {
                        Spacer(Modifier.weight(1f))

                        NavigationRailItem(
                            selected = currentRoute == AppRoutes.USER_CARDS,
                            onClick = {
                                navController.navigate(AppRoutes.USER_CARDS) {
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            },
                            icon = { Icon(painter = painterResource(Res.drawable.home), contentDescription = null) },
                            label = { Text("Moje karty") },
                        )

                        NavigationRailItem(
                            selected = currentRoute == AppRoutes.ADD_CARDS,
                            onClick = {
                                navController.navigate(AppRoutes.ADD_CARDS) {
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            },
                            icon = { Icon(painter = painterResource(Res.drawable.playing_cards), contentDescription = null) },
                            label = { Text("Dodaj karty") },
                        )
                        Spacer(Modifier.weight(1f))
                    }

                    VerticalDivider(modifier = Modifier.padding(4.dp))

                    AppNavHost(navController = navController, modifier = Modifier.weight(1f))
                }
            }
        }
    }


}
