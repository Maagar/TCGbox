package presentation.screen.userCards.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DeletePopup(cardName: String, onDelete: () -> Unit, onDismiss: () -> Unit) {
    BasicAlertDialog(modifier = Modifier.padding(16.dp), onDismissRequest = onDismiss) {
        Surface(shape = MaterialTheme.shapes.medium, color = MaterialTheme.colorScheme.surfaceVariant) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(text = "Usuń", style = MaterialTheme.typography.headlineMedium)
                Text("Czy na pewno chcesz usunąć kartę: \n$cardName?")

                Row(modifier = Modifier.fillMaxWidth(0.5f), horizontalArrangement = Arrangement.End) {
                    TextButton(modifier = Modifier.pointerHoverIcon(PointerIcon.Hand), onClick = onDismiss) {
                        Text("Anuluj")
                    }
                    TextButton(modifier = Modifier.pointerHoverIcon(PointerIcon.Hand), onClick = {
                        onDelete()
                        onDismiss()
                    }) {
                        Text("Usuń")
                    }
                }
            }
        }
    }
}