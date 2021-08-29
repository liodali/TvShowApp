package dali.hamza.tvshowapp.ui.pages.movies

import androidx.compose.foundation.layout.size
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import dali.hamza.domain.models.Movie
import dali.hamza.tvshowapp.R
import dali.hamza.tvshowapp.ui.MainActivity.Companion.moviesComposition
import dali.hamza.tvshowapp.ui.component.ListTvShow
import dali.hamza.tvshowapp.ui.component.LoadingComponent
import dali.hamza.tvshowapp.ui.component.TopAppBarApp
import kotlinx.coroutines.launch

@Composable
fun MoviesCompose() {
    val vm = moviesComposition.current.getVM()
    val navController = moviesComposition.current.getController()
    val scaffoldState = rememberScaffoldState()

    LaunchedEffect(key1 = vm.toString()) {
        vm.getMovies()
    }
    val data = vm.getUiState().collectAsState()
    Scaffold(
        topBar = {
            TopAppBarApp(
                title = stringResource(id = R.string.movies_page),
                onBack = {
                    navController.popBackStack()
                }
            )

        },
        scaffoldState = scaffoldState
    ) {
        when (data.value.isLoading) {
            true -> LoadingComponent()
            false -> {
                when (data.value.error != null) {
                    true -> Text(stringResource(id = R.string.errorMovie))
                    false -> ListTvShow(data.value.data!!, actionItemMovie = { movie ->
                        TrailingFavMovie(movie, scaffoldState)
                    })
                }
            }
        }
    }
}

@Composable
fun TrailingFavMovie(movie: Movie, scaffoldState: ScaffoldState) {
    val isAddedMovieSuccess = stringResource(id = R.string.movie_is_added_fav)
    val isRemovedMovieSuccess = stringResource(id = R.string.movie_is_removed_fav)
    val labelHideSnack = stringResource(id = R.string.hide)
    val coroutineScope = rememberCoroutineScope()

    val vm = moviesComposition.current.getVM()
    val mutableIsFav = remember {
        mutableStateOf(false)
    }
    LaunchedEffect(key1 = movie) {
        vm.checkMovieIsFav(movie = movie, onData = { fav ->
            mutableIsFav.value = fav
        })
    }
    IconButton(onClick = {
        scaffoldState.snackbarHostState.currentSnackbarData?.dismiss()

        when (mutableIsFav.value) {
            true -> {
                vm.removeMovieFromFav(movie = movie, onData = { isRemoved ->
                    mutableIsFav.value = !isRemoved
                    coroutineScope.launch {
                        scaffoldState.snackbarHostState.showSnackbar(
                            message = "${movie.title} $isRemovedMovieSuccess",
                            actionLabel = labelHideSnack,
                            duration = SnackbarDuration.Short
                        )
                    }
                })            }
            false -> {

                vm.addMovieToFav(movie = movie, onData = { isAdded ->
                    mutableIsFav.value = isAdded
                    coroutineScope.launch {
                        scaffoldState.snackbarHostState.showSnackbar(
                            message = "${movie.title} $isAddedMovieSuccess",
                            actionLabel = labelHideSnack,
                            duration = SnackbarDuration.Short
                        )
                    }
                })
            }
        }

    }, Modifier.size(48.dp)) {
        Icon(
            Icons.Default.Star,
            contentDescription = "",
            modifier = Modifier,
            tint = when (mutableIsFav.value) {
                true -> Color.Yellow
                false -> Color.Gray
            }
        )
    }
}

