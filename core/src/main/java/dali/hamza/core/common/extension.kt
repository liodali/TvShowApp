package dali.hamza.core.common

import CreateMovieMutation
import MoviesQuery
import com.apollographql.apollo.api.Input
import dali.hamza.core.datasource.db.models.MovieDb
import dali.hamza.domain.models.Movie
import type.CreateMovieFieldsInput
import type.CreateMovieInput

fun MoviesQuery.Data.toListMovies(): List<Movie> {
    return this.movies.edges!!.map { node ->
        node!!.node!!.toMovie()
    }.toList()
}

fun CreateMovieMutation.Data.toMovie(): Movie {
    return this.createMovie!!.movie.toMovie()
}

private fun CreateMovieMutation.Movie.toMovie(): Movie {
    return Movie(
        id = id,
        title = title,
        dateRelease = DateManager.formatToTime(
            this.releaseDate as String,
            formatSource = DateManager.qlFormat
        ),
        season = seasons?.toInt() ?: 0
    )
}

fun MoviesQuery.Node.toMovie(): Movie {
    return Movie(
        id = this.id,
        dateRelease = when (this.releaseDate != null) {
            true -> DateManager.formatToTime(
                this.releaseDate as String,
                formatSource = DateManager.qlFormat
            )
            false -> ""
        },
        title = this.title,
        season = this.seasons?.toInt() ?: 0,
    )
}

fun Movie.toMovieInput(): CreateMovieInput {
    return CreateMovieInput(
        fields = Input.fromNullable(
            CreateMovieFieldsInput(
                title = title,
                releaseDate = Input.fromNullable(
                    DateManager.formatToQlTime(
                        dateRelease,
                    )
                ),
                seasons = Input.fromNullable(season.toDouble())
            )
        )
    )
}

fun Movie.toMovieDb(): MovieDb {
    return MovieDb(
        id = id!!,
        title = title,
        dateRelease = DateManager.appFormat.parse(dateRelease),
        season = season
    )
}

fun MovieDb.toMovie(): Movie {
    return Movie(
        id = id,
        title = title,
        dateRelease = DateManager.appFormat.format(dateRelease),
        season = season
    )
}
