package dali.hamza.core.repository

import CreateMovieMutation
import MoviesQuery
import android.util.Log
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.coroutines.await
import dali.hamza.core.common.toListMovies
import dali.hamza.core.common.toMovie
import dali.hamza.core.common.toMovieDb
import dali.hamza.core.common.toMovieInput
import dali.hamza.core.datasource.db.dao.MovieDao
import dali.hamza.domain.models.ErrorResponse
import dali.hamza.domain.models.IResponse
import dali.hamza.domain.models.Movie
import dali.hamza.domain.models.SuccessResponse
import dali.hamza.domain.repository.IMovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MovieRepository @Inject constructor(
    private val apolloClient: ApolloClient,
    private val movieDao: MovieDao,
) : IMovieRepository {
    override suspend fun createMovie(movie: Movie): Flow<IResponse> {
        return flow {
            val call =
                apolloClient.mutate(CreateMovieMutation(movie = movie.toMovieInput())).await()
            when (call.hasErrors()) {
                true -> {
                    emit(ErrorResponse(error = "error to create ${movie.title}"))
                }
                false -> {
                    emit(SuccessResponse(data = call.data!!.toMovie()))
                }
            }
        }
    }

    override suspend fun addMovieToFac(movie: Movie): Boolean {
        return try {
            movieDao.insert(movie.toMovieDb())
            true
        } catch (e: Exception) {
            Log.e("error insert movie", e.stackTraceToString())
            false
        }

    }

    override suspend fun getMovieFav(movie: Movie): Flow<List<Movie>> {
        return movieDao.getAllMovies()
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


