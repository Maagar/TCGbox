package org.example.project

import Presentation.component.AppNavigationRail
import Presentation.navigation.AppNavHost
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.VerticalDivider
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.compose.AppTheme
import org.koin.compose.KoinContext

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

                    AppNavigationRail(navController, currentRoute)

                    VerticalDivider(modifier = Modifier.padding(4.dp))

                    AppNavHost(navController = navController, modifier = Modifier.weight(1f))
                }
            }
        }
    }


}
