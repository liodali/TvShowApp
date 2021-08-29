package dali.hamza.tvshowapp.ui.pages.home

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import dali.hamza.tvshowapp.R
import dali.hamza.tvshowapp.ui.common.onPressed
import dali.hamza.tvshowapp.ui.component.TopAppBarApp
import kotlinx.coroutines.delay

@Composable
fun Home(
    goToCreateMovie: onPressed,
    goToMovies: onPressed,
    goToFavMovies: onPressed,
) {

    Scaffold(
        topBar = {
            TopAppBarApp(
                title = stringResource(id = R.string.home_page),
            )
        }
    ) {

        Column() {
            Box(
                Modifier
                    .height(256.dp)
                    .fillMaxWidth()
                    .padding(bottom = 12.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.background),
                    contentDescription = "",
                    Modifier
                        .fillMaxHeight()
                        .fillMaxWidth(),
                    contentScale = ContentScale.FillWidth
                )
            }

            @OptIn(ExperimentalFoundationApi::class)
            LazyVerticalGrid(
                cells = GridCells.Adaptive(minSize = 172.dp),
                contentPadding = PaddingValues(top = 5.dp),
                modifier = Modifier.padding(horizontal = 12.dp)
            ) {
                item {
                    ButtonHome(
                        action = goToCreateMovie,
                        icon = R.drawable.ic_baseline_add_to_queue_24,
                        label = stringResource(id = R.string.add_movie)
                    )
                }
                item {
                    ButtonHome(
                        action = goToMovies,
                        icon = R.drawable.ic_baseline_live_tv_24,
                        label = stringResource(id = R.string.see_movie)
                    )
                }
                item {
                    ButtonHome(
                        action = goToFavMovies,
                        icon = R.drawable.ic_baseline_star_24,
                        label = stringResource(id = R.string.see_fav_movie)
                    )

                }
            }
        }

    }
}



@Composable
fun ButtonHome(
    action: onPressed,
    modifier: Modifier = Modifier.padding(2.dp),
    label: String,
    icon: Int,
) {
    Box(
        modifier = Modifier.then(modifier)
    ) {
        OutlinedButton(
            onClick = action,
            shape = RoundedCornerShape(8.dp),
            border = BorderStroke(
                width = 0.3.dp,
                color = Color.Gray
            ),
            colors = ButtonDefaults.outlinedButtonColors(
                backgroundColor = Color.Transparent,
            ),
            modifier = Modifier.size(172.dp)
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = icon),
                    contentDescription = "icon button add",
                    colorFilter = ColorFilter.tint(
                        color = when (isSystemInDarkTheme()) {
                            true -> Color.White
                            false -> Color.Black
                        }
                    ),
                    modifier = Modifier.size(48.dp)
                )
                Text(label, textAlign = TextAlign.Center)
            }
        }
    }
}