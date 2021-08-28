package dali.hamza.tvshowapp.ui.component

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dali.hamza.tvshowapp.ui.theme.Gray700

@Composable
fun EmptyMovies(
    painter: Painter,
    text: String,
) {
    Column(
        Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally

    ) {
        Icon(
            painter = painter,
            contentDescription = "",
            modifier = Modifier
                .width(56.dp)
                .height(56.dp),
            tint = when {
                isSystemInDarkTheme() -> MaterialTheme.colors.onBackground
                else -> Gray700
            }
        )
        Text(
            text,
            fontSize = 15.sp,
            modifier = Modifier.padding(vertical = 5.dp)
        )
    }
}