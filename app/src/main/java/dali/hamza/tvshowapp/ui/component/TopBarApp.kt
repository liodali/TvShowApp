package dali.hamza.tvshowapp.ui.component

import androidx.compose.foundation.layout.Row
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import dali.hamza.tvshowapp.ui.common.onPressed

@Composable
fun TopAppBarApp(
    title: String,
    onBack: onPressed
) {
    TopAppBar() {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onBack) {
                Icon(Icons.Default.ArrowBack,"")
            }
            Text(text = title)
        }
    }
}