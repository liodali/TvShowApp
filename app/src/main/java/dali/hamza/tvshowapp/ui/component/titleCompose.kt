package dali.hamza.tvshowapp.ui.component

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp

@Composable
fun TitleText(
    text: String,
    textSize: TextUnit = 18.sp,
    textColor: Color = Color.Black,
    fontWeight: FontWeight = FontWeight.Bold,
    modifier: Modifier
) {
    Text(
        text = text,
        style = MaterialTheme.typography.h1.copy(
            color = textColor,
            fontSize = textSize,
            fontWeight = fontWeight
        ),
        modifier = modifier
    )
}