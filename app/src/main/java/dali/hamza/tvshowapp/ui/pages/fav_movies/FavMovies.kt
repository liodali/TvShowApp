package dali.hamza.tvshowapp.ui.pages.fav_movies

import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import dali.hamza.domain.models.Movie
import dali.hamza.tvshowapp.R
import dali.hamza.tvshowapp.ui.MainActivity.Companion.favMoviesComposition
import dali.hamza.tvshowapp.ui.component.EmptyMovies
import dali.hamza.tvshowapp.ui.component.ListTvShow
import dali.hamza.tvshowapp.ui.component.LoadingComponent
import dali.hamza.tvshowapp.ui.component.TopAppBarApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun FavoriteMoviesCompose() {
    val viewModel = favMoviesComposition.current.getVM()
    val navController = favMoviesComposition.current.getController()
    val scaffoldState = rememberScaffoldState()
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(key1 = viewModel) {
        viewModel.getFavMovies()
    }
    val data = viewModel.getUiState().collectAsState()
    Scaffold(
        topBar = {
            TopAppBarApp(
                title = stringResource(id = R.string.fav_movies_page),
                onBack = {
                    if (scaffoldState.snackbarHostState.currentSnackbarData != null) {
                        scaffoldState.snackbarHostState.currentSnackbarData?.dismiss()
                    }
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
                        emptyView = {
                            EmptyMovies(
                                painter = painterResource(id = R.drawable.ic_baseline_star_24),
                                text = stringResource(id = R.string.emptyMoviesFav)
                            )
                        },
                        actionItemMovie = { movie ->
                            TrailingRemoveFavorite(
                                movie = movie,
                                scaffoldState = scaffoldState,
                                scope = coroutineScope
                            )
                        },
                    )
                }
            }
        }
    }
}

/**
 * this composable action that responsible to remove the movie from local database
 * logic behind removing movie that we show snackBar and we only remove the movie from list
 * and when snackBar was dismissed , we remove the movie from local database ,
 * if the user hit cancel button,we reinsert the removed movie into the list
 */
@Composable
fun TrailingRemoveFavorite(movie: Movie, scaffoldState: ScaffoldState, scope: CoroutineScope) {
    val isRemovedMovieSuccess = stringResource(id = R.string.movie_is_removed_fav)
    val isRemovedMovieFailed = stringResource(id = R.string.movie_is_removed_failed_fav)
    val labelCancelSnack = stringResource(id = android.R.string.cancel)
    val labelHideSnack = stringResource(id = R.string.hide)

    val viewModel = favMoviesComposition.current.getVM()

    IconButton(onClick = {

        scope.launch {
            if (scaffoldState.snackbarHostState.currentSnackbarData != null) {
                scaffoldState.snackbarHostState.currentSnackbarData?.dismiss()
                 delay(200)
            }
            /**
             * get the index for the movie that we will used to backup the movie
             * if the use hit cancel action
             */
            val index = viewModel.getIndexOf(movie = movie)
            viewModel.removeLocallyMovie(movie)
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
        Icon(Icons.Default.Delete, "", tint = Color.Red)
    }
}