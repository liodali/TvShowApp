package dali.hamza.tvshowapp.ui.component

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import dali.hamza.domain.models.Movie
import dali.hamza.tvshowapp.R
import dali.hamza.tvshowapp.common.isNotEmptyAndNotBlank

@Composable
fun ItemMovieCompose(
    movie: Movie,
    trailing: (@Composable () -> Unit)? = null
) {
    val dateReleaseLabelRes = stringResource(id = R.string.dateRelease)
    val undefinedLabelRes = stringResource(id = R.string.undefined)
    val seasonLabelRes = stringResource(id = R.string.season)
    Card(
        elevation = 2.dp,
        shape = RoundedCornerShape(4.dp),
        modifier = Modifier
            .padding(5.dp)
            .fillMaxWidth()
    ) {
        Row {
            Column(
                modifier = Modifier
                    .padding(8.dp)
                    .weight(
                        when (trailing != null) {
                            true -> 0.85f
                            false -> 1.0f
                        }
                    )
            ) {
                TitleText(
                    text = movie.title,
                    textColor = when (isSystemInDarkTheme()) {
                        true -> Color.White
                        false -> Color.Black
                    },
                    fontWeight = FontWeight.Normal,
                    modifier = Modifier
                )
                SubTitleItemMovie(
                    prefixSubTile = dateReleaseLabelRes,
                    subTitle = when (movie.dateRelease.isNotEmptyAndNotBlank()) {
                        true -> "\t${movie.dateRelease}"
                        false -> undefinedLabelRes
                    }
                )
                SubTitleItemMovie(
                    prefixSubTile = seasonLabelRes,
                    subTitle = "\t${movie.season}"
                )
            }
            if (trailing != null) {
                Box(
                    Modifier.weight(0.15f)
                ) {
                    trailing()
                }
            }
        }
    }
}