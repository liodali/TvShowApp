package dali.hamza.core.interactor.movies

import dali.hamza.domain.interactor.FlowUseCase
import dali.hamza.domain.models.Movie
import dali.hamza.domain.repository.IMovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class IsFavMovieInteractor @Inject constructor(
    private val repository: IMovieRepository
) : FlowUseCase<Movie, Boolean> {
    override suspend fun invoke(parameter: Movie?): Flow<Boolean> {
        return flow {
            val response = repository.isMovieFav(parameter!!)
            emit(response)
        }
    }

}