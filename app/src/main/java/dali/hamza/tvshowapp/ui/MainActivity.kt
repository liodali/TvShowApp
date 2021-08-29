package dali.hamza.tvshowapp.ui

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import dali.hamza.tvshowapp.R
import dali.hamza.tvshowapp.models.AppComposition
import dali.hamza.tvshowapp.models.Routes.Companion.rememberRoutesNames
import dali.hamza.tvshowapp.ui.common.DatePickerFragment
import dali.hamza.tvshowapp.ui.component.SplashScreen
import dali.hamza.tvshowapp.ui.pages.create_movie.CreateMovieCompose
import dali.hamza.tvshowapp.ui.pages.create_movie.CreateMovieViewModel
import dali.hamza.tvshowapp.ui.pages.fav_movies.FavMovieViewModel
import dali.hamza.tvshowapp.ui.pages.fav_movies.FavoriteMoviesCompose
import dali.hamza.tvshowapp.ui.pages.home.Home
import dali.hamza.tvshowapp.ui.pages.movies.MoviesCompose
import dali.hamza.tvshowapp.ui.pages.movies.MoviesViewModel
import dali.hamza.tvshowapp.ui.theme.TvShowAppTheme
import kotlinx.coroutines.delay

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    companion object {
        val moviesComposition =
            compositionLocalOf<AppComposition<MoviesViewModel>> { error("No viewModel found!") }
        val createMovieComposition =
            compositionLocalOf<AppComposition<CreateMovieViewModel>> { error("No viewModel found!") }
        val favMoviesComposition =
            compositionLocalOf<AppComposition<FavMovieViewModel>> { error("No viewModel found!") }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            App()
        }
    }

    @Composable
    fun App() {
        val navController = rememberNavController()
        val routes = rememberRoutesNames()
        val started = routes.home

        /**
         * state to manage show splash when app started
         */
        var showSplash by remember {
            mutableStateOf(true)
        }
        /**
         * change show splash and keep last value changed
         */
        val quitSplash by rememberUpdatedState {
            showSplash = false
        }
        LaunchedEffect("startedApp") {
            delay(2000)
            quitSplash()
        }

        TvShowAppTheme {
            Crossfade(targetState = showSplash) { showSplash ->
                when (showSplash) {
                    false -> NavHost(navController, startDestination = started) {
                        composable(routes.home) {
                            Home(
                                goToCreateMovie = {
                                    navController.navigate(routes.createMovies)
                                },
                                goToMovies = {
                                    navController.navigate(routes.movies)
                                },
                                goToFavMovies = {
                                    navController.navigate(routes.favMovies)
                                }
                            )
                        }
                        composable(routes.createMovies) {
                            val createMovieViewModel = hiltViewModel<CreateMovieViewModel>()
                            CompositionLocalProvider(
                                createMovieComposition provides AppComposition(
                                    createMovieViewModel,
                                    navController
                                )
                            ) {
                                CreateMovieCompose(
                                    openDateDialog = { onSetDate, onCancel ->
                                        DatePickerFragment(
                                            onPressed = onSetDate,
                                            onCancel = onCancel
                                        )
                                            .show(supportFragmentManager, "date release")
                                    }
                                )
                            }
                        }
                        composable(routes.movies) {
                            val moviesViewModel = hiltViewModel<MoviesViewModel>()
                            CompositionLocalProvider(
                                moviesComposition provides AppComposition(
                                    moviesViewModel,
                                    navController
                                )
                            ) {
                                MoviesCompose()
                            }
                        }
                        composable(routes.favMovies) {
                            val favMoviesViewModel = hiltViewModel<FavMovieViewModel>()
                            CompositionLocalProvider(
                                favMoviesComposition provides AppComposition(
                                    favMoviesViewModel,
                                    navController
                                )
                            ) {
                                FavoriteMoviesCompose()
                            }
                        }
                    }
                    true -> SplashScreen()
                }
            }

        }
    }


}

