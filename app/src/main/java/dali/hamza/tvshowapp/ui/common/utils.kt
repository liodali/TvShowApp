package dali.hamza.tvshowapp.ui.common

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.saveable.mapSaver
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import dali.hamza.domain.models.Movie

typealias onPressed = () -> Unit



@Composable
fun SpacerHeight(
    height: Dp = 1.dp
) {
    Spacer(
        modifier = Modifier.height(height)
    )
}

@Composable
fun SpacerWidth(
    width: Dp = 1.dp
) {
    Spacer(
        modifier = Modifier.width(width)
    )
}