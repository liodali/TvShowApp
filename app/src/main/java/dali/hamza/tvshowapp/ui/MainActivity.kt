package dali.hamza.tvshowapp.ui

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dali.hamza.tvshowapp.models.AppComposition
import dali.hamza.tvshowapp.models.Routes.Companion.rememberRoutesNames
import dali.hamza.tvshowapp.ui.pages.home.Home
import dali.hamza.tvshowapp.ui.pages.movies.MoviesViewModel
import dali.hamza.tvshowapp.ui.theme.TvShowAppTheme
import androidx.hilt.navigation.compose.hiltViewModel
import dagger.hilt.android.AndroidEntryPoint
import dali.hamza.tvshowapp.ui.common.DatePickerFragment
import dali.hamza.tvshowapp.ui.pages.create_movie.CreateMovieCompose
import dali.hamza.tvshowapp.ui.pages.create_movie.CreateMovieViewModel
import dali.hamza.tvshowapp.ui.pages.fav_movies.FavMovieViewModel
import dali.hamza.tvshowapp.ui.pages.fav_movies.FavoriteMoviesCompose
import dali.hamza.tvshowapp.ui.pages.movies.MoviesCompose

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    companion object {
        val moviesComposition = compositionLocalOf<AppComposition<MoviesViewModel>> { error("No viewModel found!") }
        val createMovieComposition = compositionLocalOf<AppComposition<CreateMovieViewModel>> { error("No viewModel found!") }
        val favMoviesComposition = compositionLocalOf<AppComposition<FavMovieViewModel>> { error("No viewModel found!") }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
          App()
        }
    }

    @Composable
    fun App(){
        val navController = rememberNavController()
        val routes = rememberRoutesNames()
        val started = routes.home
        TvShowAppTheme {
            NavHost(navController, startDestination = started ){
                composable(routes.home){
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
                composable(routes.createMovies){
                    val createMovieViewModel = hiltViewModel<CreateMovieViewModel>()
                    CompositionLocalProvider(
                        createMovieComposition provides AppComposition(
                            createMovieViewModel,
                            navController
                        )
                    ){
                        CreateMovieCompose(
                            openDateDialog = { onSetDate,onCancel ->
                                DatePickerFragment(onPressed = onSetDate, onCancel = onCancel)
                                    .show(supportFragmentManager,"date release")
                            }
                        )
                    }
                }
                composable(routes.movies){
                    val moviesViewModel = hiltViewModel<MoviesViewModel>()
                    CompositionLocalProvider(
                        moviesComposition provides AppComposition(
                            moviesViewModel,
                            navController
                        )
                    ){
                        MoviesCompose()
                    }
                }
                composable(routes.favMovies){
                    val favMoviesViewModel = hiltViewModel<FavMovieViewModel>()
                    CompositionLocalProvider(
                        favMoviesComposition provides AppComposition(
                            favMoviesViewModel,
                            navController
                        )
                    ){
                        FavoriteMoviesCompose()
                    }
                }
            }
        }
    }
}

