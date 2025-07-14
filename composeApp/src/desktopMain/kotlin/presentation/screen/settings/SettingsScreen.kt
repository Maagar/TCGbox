package presentation.screen.settings

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.koinInject
import tcgbox.composeapp.generated.resources.Res
import tcgbox.composeapp.generated.resources.refresh

@Composable
fun SettingsScreen() {
    val settingsViewModel: SettingsViewModel = koinInject()

    val isLoading by settingsViewModel.isLoading.collectAsState()

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {

        if (isLoading) {
            CircularProgressIndicator()
        }
        Card {
            Column {
                Text("Ustawienia:", modifier = Modifier.padding(8.dp), style = MaterialTheme.typography.titleLarge)
                Row(modifier = Modifier.padding(8.dp), verticalAlignment = Alignment.CenterVertically) {
                    Text("Aktualizuj dodatki: ")
                    ElevatedButton({ settingsViewModel.refreshPokemonSets() }) {
                        Icon(painterResource(Res.drawable.refresh), null)
                    }
                }

            }
        }
    }
}