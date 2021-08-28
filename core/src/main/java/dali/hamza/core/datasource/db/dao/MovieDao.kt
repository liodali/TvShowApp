package dali.hamza.core.datasource.db.dao

import androidx.room.Dao
import androidx.room.Query
import dali.hamza.core.datasource.db.models.MovieDb
import dali.hamza.domain.models.Movie
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao : AppDao<MovieDb> {

    @Query("select * from Movie")
    fun getAllMovies(): Flow<List<Movie>>
}