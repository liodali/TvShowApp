package dali.hamza.tvshowapp.ui.pages.movies

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import dali.hamza.domain.models.Movie
import dali.hamza.tvshowapp.ui.MainActivity.Companion.moviesComposition
import dali.hamza.tvshowapp.R
import dali.hamza.tvshowapp.ui.component.EmptyMovies
import dali.hamza.tvshowapp.ui.component.ItemMovieCompose
import dali.hamza.tvshowapp.ui.component.LoadingComponent

@Composable
fun MoviesCompose() {
    val vm = moviesComposition.current.getVM()
    val navController = moviesComposition.current.getController()
    DisposableEffect(key1 = vm.toString()) {
        vm.getMovies()
        onDispose {}
    }
    val data = vm.getUiState().collectAsState()
    Scaffold (
        topBar = {
            TopAppBar (
                elevation = 0.dp
                    ){
               Row(
                   verticalAlignment = Alignment.CenterVertically
               ) {
                   IconButton(onClick = {
                       navController.popBackStack()
                   }) {
                       Icon(Icons.Default.ArrowBack, contentDescription = "")
                   }
                   Text(stringResource(id = R.string.movies_page))
               }
            }
        }
            ){
        when (data.value.isLoading) {
            true -> LoadingComponent()
            false -> {
                when (data.value.error != null) {
                    true -> Text(stringResource(id = R.string.errorMovie))
                    false -> ListTvShow(data.value.data!!)
                }
            }
        }
    }
}

@Composable
fun ListTvShow(movies: List<Movie>) {
    when (movies.isEmpty()) {
        true -> EmptyMovies(
            painter = painterResource(id = R.drawable.ic_baseline_live_tv_24),
            text = stringResource(id = R.string.emptyMovies)
        )
        else -> {
            LazyColumn() {
                items(movies) { movie ->
                    ItemMovieCompose(movie)
                }
            }
        }
    }
}
