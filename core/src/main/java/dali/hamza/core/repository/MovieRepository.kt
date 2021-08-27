package dali.hamza.core.repository

import MoviesQuery
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.coroutines.await
import dali.hamza.core.common.toListMovies
import dali.hamza.domain.models.ErrorResponse
import dali.hamza.domain.models.IResponse
import dali.hamza.domain.models.Movie
import dali.hamza.domain.models.SuccessResponse
import dali.hamza.domain.repository.IMovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MovieRepository @Inject constructor(
    private val apolloClient: ApolloClient
) : IMovieRepository {
    override suspend fun createMovie(movie: Movie): Flow<IResponse> {
        TODO("Not yet implemented")
    }

    override suspend fun addMovieToFac(movie: Movie): Flow<IResponse> {
        TODO("Not yet implemented")
    }

    override suspend fun getMovieFav(movie: Movie): Flow<IResponse> {
        TODO("Not yet implemented")
    }

    override suspend fun searchMovie(name: String?, dateRelease: String?): Flow<IResponse> {
        TODO("Not yet implemented")
    }

    override suspend fun getAll(): Flow<IResponse> {
        return flow {
            val call = apolloClient.query(MoviesQuery()).await()
            if (!call.hasErrors()) {
                val data = call.data!!.toListMovies()
                emit(SuccessResponse(data = data))
            } else {
                emit(ErrorResponse(error = call.errors!!.map {
                    it.message
                }.reduce { acc, s -> "$acc \n $s\n" }.toString()))
            }
        }
    }
}