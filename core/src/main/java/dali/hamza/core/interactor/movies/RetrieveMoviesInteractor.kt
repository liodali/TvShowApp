package dali.hamza.core.interactor.movies

import dali.hamza.domain.interactor.FlowIResponseUseCase0
import dali.hamza.domain.models.IResponse
import dali.hamza.domain.repository.IMovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RetrieveMoviesInteractor @Inject constructor(
    private val repository: IMovieRepository
) : FlowIResponseUseCase0 {
    override suspend fun invoke(): Flow<IResponse> {
        return repository.getAll()
    }
}