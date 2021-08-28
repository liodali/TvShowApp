package dali.hamza.core.interactor.movies

import dali.hamza.domain.interactor.FlowUseCase0
import dali.hamza.domain.models.Movie
import dali.hamza.domain.repository.IMovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetAllFavMoviesInteractor @Inject constructor(
    private val repository: IMovieRepository
) : FlowUseCase0<List<Movie>> {
    override suspend fun invoke(): Flow<List<Movie>> {
        return repository.getAllMoviesFav()
    }

}