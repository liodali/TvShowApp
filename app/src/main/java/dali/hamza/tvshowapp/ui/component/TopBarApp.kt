package dali.hamza.tvshowapp.ui.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dali.hamza.tvshowapp.ui.common.onPressed

@Composable
fun TopAppBarApp(
    title: String,
    onBack: (onPressed?) = null
) {
    TopAppBar() {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            when (onBack != null) {
                true -> IconButton(onClick = onBack) {
                    Icon(Icons.Default.ArrowBack, "")
                }
                false -> Box(Modifier.size(48.dp))
            }

            Text(text = title)
        }
    }
}