package dali.hamza.tvshowapp.ui.component

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import dali.hamza.domain.models.Movie
import dali.hamza.tvshowapp.R

@Composable
fun ListTvShow(
    movies: List<Movie>,
    emptyView: @Composable () -> Unit = {
        EmptyMovies(
            painter = painterResource(id = R.drawable.ic_baseline_live_tv_24),
            text = stringResource(id = R.string.emptyMovies)
        )
    },
    actionItemMovie: (@Composable (Movie) -> Unit)? = null
) {
    when (movies.isEmpty()) {
        true -> emptyView()
        else -> {
            LazyColumn {
                items(movies) { movie ->
                    ItemMovieCompose(movie, trailing = if (actionItemMovie != null) {
                        { actionItemMovie(movie) }
                    } else null)
                }
            }
        }
    }
}