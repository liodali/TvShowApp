package dali.hamza.core.common

import MoviesQuery
import dali.hamza.domain.models.Movie

fun MoviesQuery.Data.toListMovies(): List<Movie> {
    return this.movies.edges!!.map { node ->
        node!!.node!!.toMovie()
    }.toList()
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
