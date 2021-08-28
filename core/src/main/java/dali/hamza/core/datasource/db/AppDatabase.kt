package dali.hamza.core.datasource.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import dali.hamza.core.datasource.db.dao.MovieDao
import dali.hamza.core.datasource.db.models.MovieDb

@Database(
    version = 1,
    entities = [
        MovieDb::class
    ]
)
@TypeConverters(Converter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun MovieDao(): MovieDao
}