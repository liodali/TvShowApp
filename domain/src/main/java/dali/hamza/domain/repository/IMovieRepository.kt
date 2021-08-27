package dali.hamza.domain.repository

import dali.hamza.domain.models.IResponse
import dali.hamza.domain.models.Movie
import kotlinx.coroutines.flow.Flow

interface IMovieRepository : IRepository<Movie> {

    suspend fun createMovie(movie: Movie): Flow<IResponse>
    suspend fun addMovieToFac(movie: Movie): Flow<IResponse>
    suspend fun getMovieFav(movie: Movie): Flow<IResponse>
    suspend fun searchMovie(name: String?, dateRelease: String?): Flow<IResponse>


}