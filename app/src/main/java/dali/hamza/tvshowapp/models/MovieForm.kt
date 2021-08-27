package dali.hamza.tvshowapp.models

import androidx.compose.runtime.saveable.mapSaver
import dali.hamza.domain.models.Movie

data class MovieForm(
    val title: String,
    val dateRelease: String,
    val season: String
)

fun initOfMovieForm(): MovieForm {
    return MovieForm(
        title = "",
        dateRelease = "",
        season = ""
    )
}

fun MovieForm.toMovie(): Movie {
    return Movie(
        id = null,
        title = title,
        dateRelease = dateRelease,
        season = season.toInt()
    )
}

val MovieSaver = run {
    val titleKey = "title"
    val dateReleaseKey = "dateRelease"
    val seasonKey = "season"
    mapSaver(
        save = {
            mapOf(
                titleKey to it.title,
                dateReleaseKey to it.dateRelease,
                seasonKey to it.season
            )
        },
        restore = {
            MovieForm(
                it[titleKey] as String,
                it[dateReleaseKey] as String,
                it[seasonKey] as String
            )
        }
    )
}


