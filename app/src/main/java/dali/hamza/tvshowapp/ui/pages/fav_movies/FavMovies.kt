package dali.hamza.tvshowapp.ui.pages.fav_movies

import android.util.Log
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.res.stringResource
import dali.hamza.domain.models.Movie
import dali.hamza.tvshowapp.R
import dali.hamza.tvshowapp.ui.MainActivity.Companion.favMoviesComposition
import dali.hamza.tvshowapp.ui.component.ListTvShow
import dali.hamza.tvshowapp.ui.component.LoadingComponent
import dali.hamza.tvshowapp.ui.component.TopAppBarApp
import kotlinx.coroutines.launch

@Composable
fun FavoriteMoviesCompose() {
    val viewModel = favMoviesComposition.current.getVM()
    val navController = favMoviesComposition.current.getController()
    val scaffoldState = rememberScaffoldState()
    LaunchedEffect(key1 = viewModel) {
        viewModel.getFavMovies()
    }
    val data = viewModel.getUiState().collectAsState()
    Scaffold(
        topBar = {
            TopAppBarApp(
                title = stringResource(id = R.string.fav_movies_page),
                onBack = {
                    navController.popBackStack()
                }
            )
        }, scaffoldState = scaffoldState
    ) {
        when (data.value.isLoading) {
            true -> LoadingComponent()
            false -> {
                when (data.value.error != null) {
                    true -> Text(stringResource(id = R.string.errorMovie))
                    false -> ListTvShow(
                        data.value.data!!,
                        actionItemMovie = { movie ->
                            TrailingRemoveFavorite(
                                movie = movie,
                                scaffoldState = scaffoldState
                            )
                        },
                    )
                }
            }
        }
    }
}

@Composable
fun TrailingRemoveFavorite(movie: Movie, scaffoldState: ScaffoldState) {
    val viewModel = favMoviesComposition.current.getVM()
    val isRemovedMovieSuccess = stringResource(id = R.string.movie_is_removed_fav)
    val isRemovedMovieFailed = stringResource(id = R.string.movie_is_removed_failed_fav)
    val labelCancelSnack = stringResource(id = android.R.string.cancel)
    val labelHideSnack = stringResource(id = R.string.hide)
    val coroutineScope = rememberCoroutineScope()
    IconButton(onClick = {
        scaffoldState.snackbarHostState.currentSnackbarData?.dismiss()
        val index = viewModel.removeLocallyMovie(movie)
        Log.d("movie", "$index")
        coroutineScope.launch {
            val snackResult = scaffoldState.snackbarHostState.showSnackbar(
                message = "${movie.title} $isRemovedMovieSuccess",
                actionLabel = labelCancelSnack,
                duration = SnackbarDuration.Short
            )
            when (snackResult) {
                SnackbarResult.Dismissed -> {
                    viewModel.removeMovieFromFavorite(movie, onError = {
                        viewModel.backUpMovieToFavoriteLocally(
                            movie,
                            index
                        )
                        scaffoldState.snackbarHostState.showSnackbar(
                            message = "${movie.title} $isRemovedMovieFailed",
                            actionLabel = labelHideSnack,
                            duration = SnackbarDuration.Short
                        )
                    })

                }
                SnackbarResult.ActionPerformed -> viewModel.backUpMovieToFavoriteLocally(
                    movie,
                    index
                )

            }
        }
    }) {
        Icon(Icons.Default.Delete, "")
    }
}