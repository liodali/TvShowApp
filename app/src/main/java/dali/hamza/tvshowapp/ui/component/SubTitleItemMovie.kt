package dali.hamza.tvshowapp.ui.component

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.sp
import dali.hamza.tvshowapp.ui.theme.Gray400

@Composable
fun SubTitleItemMovie(
    prefixSubTile:String,
    subTitle:String,
){
    Text(
        buildAnnotatedString {
            withStyle(
                style = SpanStyle(
                    color = when (isSystemInDarkTheme()) {
                        true -> Color.White
                        false -> Color.Black
                    },
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            ) {
                append(prefixSubTile)
            }
            withStyle(
                style = SpanStyle(
                    color = when (isSystemInDarkTheme()) {
                        true -> Gray400
                        false -> Color.Gray
                    },
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Normal
                )
            ) {
                append(subTitle)
            }
        }
    )
}