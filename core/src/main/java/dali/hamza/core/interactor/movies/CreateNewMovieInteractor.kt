package dali.hamza.core.interactor.movies

import dali.hamza.domain.interactor.FlowIResponseUseCase
import dali.hamza.domain.models.IResponse
import dali.hamza.domain.models.Movie
import dali.hamza.domain.repository.IMovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CreateNewMovieInteractor @Inject constructor(
    private val repository: IMovieRepository
) : FlowIResponseUseCase<Movie> {
    override suspend fun invoke(parameter: Movie?): Flow<IResponse> {
        return repository.createMovie(movie = parameter!!)
    }
}