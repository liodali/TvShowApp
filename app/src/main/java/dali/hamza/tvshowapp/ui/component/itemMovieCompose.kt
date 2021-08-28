package dali.hamza.tvshowapp.ui.component

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dali.hamza.domain.models.Movie
import dali.hamza.tvshowapp.R
import dali.hamza.tvshowapp.common.isNotEmptyAndNotBlank
import dali.hamza.tvshowapp.ui.theme.Gray400

@Composable
fun ItemMovieCompose(movie: Movie) {
    val dateReleaseLabelRes = stringResource(id = R.string.dateRelease)
    val undefinedLabelRes = stringResource(id = R.string.undefined)
    val seasonLabelRes = stringResource(id = R.string.season)
    Card(
        elevation = 2.dp,
        shape = RoundedCornerShape(4.dp),
        modifier = Modifier
            .padding(5.dp)
            .fillMaxWidth()
    ){
        Column(
            modifier = Modifier
                .padding(8.dp)
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
                    ){
                        append(dateReleaseLabelRes)
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
                    ){
                        when(movie.dateRelease.isNotEmptyAndNotBlank()){
                            true ->  append("\t${movie.dateRelease}")
                            false -> append(undefinedLabelRes)
                        }

                    }
                }
            )
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
                    ){
                        append(seasonLabelRes)
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
                    ){
                        append("\t${movie.season}")
                    }
                }
            )
        }
    }
}