package dali.hamza.tvshowapp.ui.pages.fav_movies

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dali.hamza.core.interactor.movies.GetAllFavMoviesInteractor
import dali.hamza.domain.models.Movie
import dali.hamza.tvshowapp.models.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavMovieViewModel @Inject constructor(
    private val getAllFavMoviesInteractor: GetAllFavMoviesInteractor
) : ViewModel() {

    private val mutableStateFlow: MutableStateFlow<UiState<List<Movie>>> =
        MutableStateFlow(UiState.initialization())
    private val stateFlow: StateFlow<UiState<List<Movie>>> = mutableStateFlow

    fun getUiState() = stateFlow

    fun getFavMovies() {
        viewModelScope.launch {
            getAllFavMoviesInteractor.invoke()
                .catch {
                mutableStateFlow.value = UiState(false, null, it)

            }.collect { movies ->
                mutableStateFlow.value = UiState(false, movies, null)

            }
        }
    }
}