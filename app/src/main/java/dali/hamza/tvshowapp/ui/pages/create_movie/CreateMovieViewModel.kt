package dali.hamza.tvshowapp.ui.pages.create_movie

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import dali.hamza.core.common.DateManager
import dali.hamza.domain.models.Movie
import dali.hamza.tvshowapp.models.MovieForm
import dali.hamza.tvshowapp.models.initOfMovieForm
import java.util.*

class CreateMovieViewModel : ViewModel() {


    var mutableFlowAutoWalletForm: MovieForm by mutableStateOf(initOfMovieForm())
        private set

    fun changeTitleMovie(title: String) {
        mutableFlowAutoWalletForm = mutableFlowAutoWalletForm.copy(
            title = title
        )
    }

    fun changeSeason(season: String) {
        mutableFlowAutoWalletForm = mutableFlowAutoWalletForm.copy(
            season = season
        )
    }

    fun changeReleaseDate(releaseDate: Date) {
        val date = DateManager.formatToTime(
            DateManager.qlFormat.format(releaseDate),
            DateManager.qlFormat,
        )
        mutableFlowAutoWalletForm = mutableFlowAutoWalletForm.copy(
            dateRelease = date
        )
    }

}