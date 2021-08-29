package dali.hamza.tvshowapp.ui.pages.fav_movies

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dali.hamza.core.interactor.movies.GetAllFavMoviesInteractor
import dali.hamza.core.interactor.movies.RemoveMovieFromFavInteractor
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
    private val getAllFavMoviesInteractor: GetAllFavMoviesInteractor,
    private val removeMovieFromFavInteractor: RemoveMovieFromFavInteractor
) : ViewModel() {

    private val mutableStateFlow: MutableStateFlow<UiState<List<Movie>>> =
        MutableStateFlow(UiState.initialization())
    private val stateFlow: StateFlow<UiState<List<Movie>>> = mutableStateFlow

    fun getUiState() = stateFlow

    fun removeLocallyMovie(movie: Movie): Int {
        val list = mutableStateFlow.value.data!!.toMutableList()
        val index = list.indexOf(movie)
        if (index != -1) {
            list.removeAt(index)
            mutableStateFlow.value = mutableStateFlow.value.copy(
                data = list.toList()
            )
        }

        return index
    }

    fun backUpMovieToFavoriteLocally(movie: Movie, index: Int) {
        val list = mutableStateFlow.value.data!!.toMutableList()
        list.add(index, movie)
        mutableStateFlow.value = mutableStateFlow.value.copy(
            data = list.toList()
        )

    }

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

    fun removeMovieFromFavorite(movie: Movie, onError: suspend () -> Unit) {
        viewModelScope.launch {
            removeMovieFromFavInteractor.invoke(movie)
                .collect { isRemoved ->
                    if (!isRemoved) {
                        onError()
                    }
                }
        }
    }
}