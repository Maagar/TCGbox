package presentation.screen.userCards.component

import androidx.compose.material.DropdownMenu
import androidx.compose.material.Text
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon

@Composable
fun CardMenu(showMenu: Boolean, onDismiss: () -> Unit, onEdit: () -> Unit, onDelete: () -> Unit) {
    DropdownMenu(
        expanded = showMenu,
        onDismissRequest = onDismiss,
    ) {
        val menuItemModifier = Modifier.pointerHoverIcon(PointerIcon.Hand)
        DropdownMenuItem(modifier = menuItemModifier, text = { Text("Edytuj kartę") }, onClick = {
            onEdit()
            onDismiss()
        })
        DropdownMenuItem(modifier = menuItemModifier, text = { Text("Usuń kartę") }, onClick = {
            onDelete()
            onDismiss()
        })

    }
}