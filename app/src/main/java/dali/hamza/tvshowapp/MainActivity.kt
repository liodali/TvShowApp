package dali.hamza.tvshowapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dali.hamza.tvshowapp.models.Routes.Companion.rememberRoutesNames
import dali.hamza.tvshowapp.ui.pages.Home
import dali.hamza.tvshowapp.ui.theme.TvShowAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
          App()
        }
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

                    },
                    goToMovies = {

                    }
                )
            }
            composable(routes.createMovies){

            }
            composable(routes.movies){

            }
            composable(routes.favMovies){

            }
        }
    }
}