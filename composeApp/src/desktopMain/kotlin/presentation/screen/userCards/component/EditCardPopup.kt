package presentation.screen.userCards.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.DefaultAlpha
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import com.tcgbox.database.Cards
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import org.jetbrains.compose.resources.painterResource
import tcgbox.composeapp.generated.resources.Res
import tcgbox.composeapp.generated.resources.calendar
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditCardPopUp(cardToEdit: Cards, onDismiss: () -> Unit, onSaveCard: (Cards) -> Unit) {
    var showError by remember { mutableStateOf(false) }
    var showLargeImage by remember { mutableStateOf(false) }
    var showDatePicker by remember { mutableStateOf(false) }

    val initialDateMillis = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).parse(cardToEdit.addedDate)?.time
    val datePickerState = rememberDatePickerState(initialSelectedDateMillis = initialDateMillis)

    val defaultDateText = "Data dodania"
    val selectedDateText = remember(datePickerState.selectedDateMillis) {
        datePickerState.selectedDateMillis?.let {
            SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(Date(it))
        } ?: defaultDateText
    }

    var isReverseHolo by remember { mutableStateOf(cardToEdit.isReverseHolo == 1L) }
    var boughtPrice by remember {
        mutableStateOf(String.format(Locale.getDefault(), "%.2f", cardToEdit.boughtPriceCents?.toDouble()?.div(100.0) ?: 0.0))
    }

    BasicAlertDialog(onDismissRequest = onDismiss, Modifier, DialogProperties()) {
        Surface {
            Row(modifier = Modifier.padding(16.dp).fillMaxWidth()) {
                KamelImage(
                    { asyncPainterResource(data = cardToEdit.imageLarge) },
                    contentDescription = null,
                    modifier = Modifier.size(300.dp).clickable(onClick = { showLargeImage = true })
                        .pointerHoverIcon(PointerIcon.Hand),
                    alignment = Alignment.Center,
                    contentScale = ContentScale.Fit,
                    alpha = DefaultAlpha,
                    contentAlignment = Alignment.Center
                )
                Column(modifier = Modifier.padding(8.dp)) {
                    Text("Nazwa: ${cardToEdit.name}", style = MaterialTheme.typography.headlineSmall)
                    Text("Dodatek: ${cardToEdit.setName}")
                    Text("Numer: ${cardToEdit.number}")

                    Text(
                        "Cena rynkowa: ${
                            if (cardToEdit.trendPriceCents != null) {
                                if (isReverseHolo && cardToEdit.reverseHoloTrendCents != null) {
                                    cardToEdit.reverseHoloTrendCents.toDouble() / 100
                                } else {
                                    cardToEdit.trendPriceCents.toDouble() / 100
                                }
                            } else "N/A"
                        } €"
                    )

                    OutlinedButton(
                        onClick = { showDatePicker = true },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 16.dp)
                            .pointerHoverIcon(PointerIcon.Hand),
                    ) {
                        Icon(
                            painterResource(Res.drawable.calendar),
                            contentDescription = null,
                            modifier = Modifier.padding(end = 8.dp)
                        )
                        Text(selectedDateText)
                    }

                    OutlinedTextField(
                        value = boughtPrice,
                        onValueChange = { newValue ->
                            val cleanedValue = newValue
                                .replace(",", ".")
                                .filter { it.isDigit() || it == '.' }
                            val parts = cleanedValue.split(".")
                            var finalValue = if (parts.size > 2) {
                                "${parts[0]}.${parts[1].take(2)}"
                            } else {
                                cleanedValue
                            }
                            if (finalValue.contains('.')) {
                                val decimalPart = finalValue.substringAfter('.')
                                if (decimalPart.length > 2) {
                                    finalValue = finalValue.substringBefore('.') + "." + decimalPart.take(2)
                                }
                            }
                            boughtPrice = finalValue
                            showError = false
                        },
                        label = { Text("Cena zakupu") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
                    )
                    val reverseHoloTrend = cardToEdit.reverseHoloTrendCents ?: 0L

                    if (reverseHoloTrend > 0) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Checkbox(
                                checked = isReverseHolo,
                                onCheckedChange = { isReverseHolo = it }
                            )
                            Text(text = "Reverse Holo")
                        }
                    }

                    Row {
                        Spacer(modifier = Modifier.weight(1f))

                        FloatingActionButton(modifier = Modifier.padding(vertical = 8.dp), onClick = {
                            if (selectedDateText.isEmpty() || selectedDateText == defaultDateText || boughtPrice.isEmpty()) {
                                showError = true
                            } else {
                                val updatedCard = cardToEdit.copy(
                                    addedDate = selectedDateText,
                                    boughtPriceCents = (boughtPrice.toDouble() * 100).toLong(),
                                    isReverseHolo = if (isReverseHolo) 1 else 0
                                )
                                onSaveCard(updatedCard)
                                onDismiss()
                            }
                        }) {
                            Text("Zapisz zmiany", modifier = Modifier.padding(8.dp))
                        }
                    }
                    if (showError) {
                        Text(
                            text = if (selectedDateText == defaultDateText) "Nie uzupełniono daty" else "Nie uzupełniono ceny",
                            modifier = Modifier.padding(8.dp),
                            color = MaterialTheme.colorScheme.error
                        )
                    }

                }
            }
        }
    }

    if (showLargeImage) {
        Popup(onDismissRequest = { showLargeImage = false }, properties = PopupProperties(focusable = true)) {
            KamelImage(
                { asyncPainterResource(data = cardToEdit.imageLarge) },
                contentDescription = null,
                modifier = Modifier.padding(40.dp).clickable { showLargeImage = false },
                alignment = Alignment.Center,
                contentScale = ContentScale.Fit,
                alpha = DefaultAlpha,
                contentAlignment = Alignment.Center
            )
        }
    }

    if (showDatePicker) {
        DatePickerDialog(
            onDismissRequest = { showDatePicker = false },
            confirmButton = {
                TextButton(onClick = { showDatePicker = false }) {
                    Text("OK")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDatePicker = false }) {
                    Text("Anuluj")
                }
            }
        ) {
            DatePicker(datePickerState)
        }
    }
}