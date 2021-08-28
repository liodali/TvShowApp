package dali.hamza.tvshowapp.ui.pages.movies

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dali.hamza.core.interactor.movies.AddMovieToFavInteractor
import dali.hamza.core.interactor.movies.IsFavMovieInteractor
import dali.hamza.core.interactor.movies.RemoveMovieFromFavInteractor
import dali.hamza.core.interactor.movies.RetrieveMoviesInteractor
import dali.hamza.domain.models.Movie
import dali.hamza.tvshowapp.common.onData
import dali.hamza.tvshowapp.models.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel @Inject constructor(
    private val retrieveMoviesInteractor: RetrieveMoviesInteractor,
    private val addMovieToFavInteractor: AddMovieToFavInteractor,
    private val isFavMovieInteractor: IsFavMovieInteractor,
    private val removeMovieFromFavInteractor: RemoveMovieFromFavInteractor
) : ViewModel() {

    private val mutableStateFlow: MutableStateFlow<UiState<List<Movie>>> =
        MutableStateFlow(UiState.initialization())
    private val stateFlow: StateFlow<UiState<List<Movie>>> = mutableStateFlow

    fun getUiState() = stateFlow

    fun getMovies() {
        viewModelScope.launch {
            retrieveMoviesInteractor.invoke().onData(
                error = {
                    mutableStateFlow.value = UiState(false, null, it)
                }
            ) { response ->
                mutableStateFlow.value = UiState(false, response.data as List<Movie>, null)

            }
        }
    }

    fun addMovieToFav(movie: Movie, onData: (Boolean) -> Unit) {
        viewModelScope.launch {
            addMovieToFavInteractor.invoke(movie)
                .collect {
                    onData(it)
                }
        }
    }
    fun removeMovieFromFav(movie: Movie, onData: (Boolean) -> Unit) {
        viewModelScope.launch {
            removeMovieFromFavInteractor.invoke(movie)
                .collect {
                    onData(it)
                }
        }
    }
    fun checkMovieIsFav(movie: Movie, onData: (Boolean) -> Unit) {
        viewModelScope.launch {
            isFavMovieInteractor.invoke(movie)
                .collect {
                    onData(it)
                }
        }
    }

}