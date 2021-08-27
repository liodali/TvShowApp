package dali.hamza.tvshowapp.ui.pages.create_movie

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dali.hamza.core.common.DateManager
import dali.hamza.core.interactor.movies.CreateNewMovieInteractor
import dali.hamza.domain.models.Movie
import dali.hamza.tvshowapp.common.onData
import dali.hamza.tvshowapp.models.MovieForm
import dali.hamza.tvshowapp.models.UiState
import dali.hamza.tvshowapp.models.initOfMovieForm
import dali.hamza.tvshowapp.models.toMovie
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class CreateMovieViewModel @Inject constructor(
    private val createMovieInteractor: CreateNewMovieInteractor
) : ViewModel() {


   private  var mutableFlowCreatedMovie: MutableStateFlow<UiState<Movie>> = MutableStateFlow(UiState(false, null, null))
    private  var flowCreatedMovie: MutableStateFlow<UiState<Movie>> = mutableFlowCreatedMovie

    fun movieCreation() = flowCreatedMovie

    var mutableFlowMovieForm: MovieForm by mutableStateOf(initOfMovieForm())
        private set

    fun changeTitleMovie(title: String) {
        mutableFlowMovieForm = mutableFlowMovieForm.copy(
            title = title
        )
    }

    fun changeSeason(season: String) {
        mutableFlowMovieForm = mutableFlowMovieForm.copy(
            season = season
        )
    }

    fun changeReleaseDate(releaseDate: Date) {
        val date = DateManager.formatToTime(
            DateManager.qlFormat.format(releaseDate),
            DateManager.qlFormat,
        )
        mutableFlowMovieForm = mutableFlowMovieForm.copy(
            dateRelease = date
        )
    }

    fun createMovie() {
        viewModelScope.launch {
            mutableFlowCreatedMovie.value = UiState.initialization()
            createMovieInteractor.invoke(mutableFlowMovieForm.toMovie()).onData(
                error = {
                    mutableFlowCreatedMovie.value = UiState(false, null, it)
                }
            ) { response ->
                mutableFlowCreatedMovie.value = UiState(false, response.data as Movie, null)
            }
        }
    }

    fun closeMovieDialogCreation() {
        mutableFlowCreatedMovie.value = UiState(false, null, null)
    }

}