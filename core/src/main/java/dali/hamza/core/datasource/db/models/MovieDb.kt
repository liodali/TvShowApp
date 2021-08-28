package dali.hamza.core.datasource.db.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(
    tableName = "Movie"
)
data class MovieDb(
    @PrimaryKey val id: String,
    val title:String,
    val dateRelease: Date,
    val season: Int,
)
