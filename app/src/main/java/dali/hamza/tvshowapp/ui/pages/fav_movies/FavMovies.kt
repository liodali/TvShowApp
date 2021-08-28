package dali.hamza.tvshowapp.ui.pages.fav_movies

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.res.stringResource
import dali.hamza.tvshowapp.R
import dali.hamza.tvshowapp.ui.MainActivity.Companion.favMoviesComposition
import dali.hamza.tvshowapp.ui.component.ListTvShow
import dali.hamza.tvshowapp.ui.component.LoadingComponent
import dali.hamza.tvshowapp.ui.component.TopAppBarApp

@Composable
fun FavoriteMoviesCompose() {
    val vm = favMoviesComposition.current.getVM()
    val navController = favMoviesComposition.current.getController()
    DisposableEffect(key1 = vm.toString()) {
        vm.getFavMovies()
        onDispose {}
    }
    val data = vm.getUiState().collectAsState()
    Scaffold(
        topBar = {
            TopAppBarApp(
                title = stringResource(id = R.string.fav_movies_page),
                onBack = {
                    navController.popBackStack()
                }
            )
        }
    ) {
        when (data.value.isLoading) {
            true -> LoadingComponent()
            false -> {
                when (data.value.error != null) {
                    true -> Text(stringResource(id = R.string.errorMovie))
                    false -> ListTvShow(
                        data.value.data!!,
                        actionItemMovie = { movie ->
                            IconButton(onClick = {

                            }) {
                                Icon(Icons.Default.Delete, "")
                            }
                        },
                    )
                }
            }
        }
    }
}