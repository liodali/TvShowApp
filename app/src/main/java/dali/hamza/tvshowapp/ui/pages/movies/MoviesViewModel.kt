package dali.hamza.tvshowapp.ui.pages.movies

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
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
    private val retrieveMoviesInteractor: RetrieveMoviesInteractor
) : ViewModel() {

    private val mutableStateFlow: MutableStateFlow<UiState<List<Movie>>> =
        MutableStateFlow(UiState.initialization())
    private val stateFlow: StateFlow<UiState<List<Movie>>> = mutableStateFlow

   fun  getUiState() = stateFlow

    fun getMovies() {
        viewModelScope.launch {
            retrieveMoviesInteractor.invoke().onData(
                error = {
                    mutableStateFlow.value = UiState(false,null,it)
                }
            ){ response ->
                mutableStateFlow.value = UiState(false,response.data as List<Movie>,null)

            }
        }
    }

}