package Presentation.screen.addCards.component

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
import androidx.compose.material3.OutlinedButton // Import OutlinedButton
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
import data.api.model.ApiCard
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
fun AddCardPopUp(card: ApiCard, onDismiss: () -> Unit, onAddCard: (Cards, (Boolean) -> Unit) -> Unit) {
    var showError by remember { mutableStateOf(false) }
    var showLargeImage by remember { mutableStateOf(false) }
    var showDatePicker by remember { mutableStateOf(false) }
    val datePickerState = rememberDatePickerState()
    val defaultDateText = "Data dodania"
    val selectedDateText = remember(datePickerState.selectedDateMillis) {
        datePickerState.selectedDateMillis?.let {
            SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(Date(it))
        } ?: defaultDateText
    }

    var isReverseHolo by remember { mutableStateOf(false) }
    var boughtPrice by remember { mutableStateOf("") }

    BasicAlertDialog(onDismissRequest = onDismiss, Modifier, DialogProperties()) {
        Surface {
            Row(modifier = Modifier.padding(16.dp).fillMaxWidth()) {
                KamelImage(
                    { asyncPainterResource(data = card.images.large) },
                    contentDescription = null,
                    modifier = Modifier.size(300.dp).clickable(onClick = { showLargeImage = true })
                        .pointerHoverIcon(PointerIcon.Hand),
                    alignment = Alignment.Center,
                    contentScale = ContentScale.Fit,
                    alpha = DefaultAlpha,
                    contentAlignment = Alignment.Center
                )
                Column(modifier = Modifier.padding(8.dp)) {
                    Text("Nazwa: ${card.name}", style = MaterialTheme.typography.headlineSmall)
                    Text("Dodatek: ${card.set.name}")
                    Text("Numer: ${card.number}/${card.set.printedTotal}")
                    Text(
                        "Cena: ${
                            if (card.cardmarket != null) {
                                if (isReverseHolo) {
                                    card.cardmarket.prices.reverseHoloTrend.toDouble() / 100
                                } else {
                                    card.cardmarket.prices.trendPrice.toDouble() / 100
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
                    val reverseHoloTrend = card.cardmarket?.prices?.reverseHoloTrend ?: 0L

                    if (reverseHoloTrend > 0) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Checkbox(
                                checked = isReverseHolo,
                                onCheckedChange = { isReverseHolo = !isReverseHolo }
                            )
                            Text(text = "Reverse Holo")
                        }

                    }

                    Row {
                        Spacer(modifier = Modifier.weight(1f))

                        FloatingActionButton(modifier = Modifier.padding(vertical = 8.dp), onClick = {
                            if (selectedDateText.isEmpty() || boughtPrice.isEmpty()) {
                                showError = true
                            } else {
                                val cardToAdd = Cards(
                                    id = 0,
                                    cardId = card.id,
                                    name = card.name,
                                    number = card.number,
                                    imageSmall = card.images.small,
                                    imageLarge = card.images.large,
                                    setId = card.set.id,
                                    setName = card.set.name,
                                    cardMarketUrl = card.cardmarket?.url,
                                    trendPriceCents = card.cardmarket?.prices?.trendPrice,
                                    reverseHoloTrendCents = card.cardmarket?.prices?.reverseHoloTrend,
                                    addedDate = selectedDateText,
                                    boughtPriceCents = (boughtPrice.toDouble() * 100).toLong(),
                                    isReverseHolo = if (isReverseHolo) 1 else 0
                                )
                                onAddCard(cardToAdd) { success ->
                                    onDismiss()
                                }
                            }
                        }) {
                            Text("Dodaj kartę", modifier = Modifier.padding(8.dp))
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
                { asyncPainterResource(data = card.images.large) },
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