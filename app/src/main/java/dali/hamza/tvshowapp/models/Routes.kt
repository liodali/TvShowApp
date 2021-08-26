package dali.hamza.tvshowapp.models

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.res.stringResource
import dali.hamza.tvshowapp.R

class Routes {

    internal var home = ""
    internal var movies = ""
    internal var createMovies = ""
    internal var favMovies = ""

    companion object {
        @Composable
        fun rememberRoutesNames(): Routes {
            val routes by remember {
                mutableStateOf(Routes())
            }
            routes.home = stringResource(id = R.string.home_page)
            routes.movies = stringResource(id = R.string.movies_page)
            routes.createMovies = stringResource(id = R.string.create_movie_page)
            routes.favMovies = stringResource(id = R.string.fav_movies_page)
            return routes
        }
    }

}