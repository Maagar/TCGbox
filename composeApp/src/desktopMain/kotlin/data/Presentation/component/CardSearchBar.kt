package data.Presentation.component

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SearchBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CardSearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
) {
    SearchBar(
        query = query,
        onQueryChange = {},
        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp),
        onSearch = {},
        active = false,
        onActiveChange = {},
        windowInsets = WindowInsets(top = 0.dp),
    ) {

    }
}