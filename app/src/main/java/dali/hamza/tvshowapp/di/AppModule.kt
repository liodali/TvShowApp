package dali.hamza.tvshowapp.di

import android.app.Application
import androidx.room.Room
import com.apollographql.apollo.ApolloClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dali.hamza.core.datasource.db.AppDatabase
import dali.hamza.tvshowapp.R
import dali.hamza.tvshowapp.common.AuthorizationInterceptor
import okhttp3.OkHttpClient
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(
    SingletonComponent::class
)
object AppModule {


    @Provides
    @Named("dbname")
    fun provideDbName(application: Application) =
        application.resources.getString(R.string.db_name)

    @Provides
    fun provideHeaders(application: Application): HashMap<String, String> {
        val header = HashMap<String, String>()
        header["X-Parse-Client-Key"] = application.getString(R.string.client_key)
        header["X-Parse-Application-Id"] = application.getString(R.string.application_id)

        return header
    }


    @Provides
    fun provideBaseUrl(application: Application) =
        application.getString(R.string.serverUrl)

    @Provides
    @Singleton
    fun provideApolloClient(
        BASE_URL: String,
        HEADERS: HashMap<String, String>
    ): ApolloClient = ApolloClient
        .builder().serverUrl(BASE_URL)
        .okHttpClient(
            OkHttpClient.Builder()
                .addInterceptor(AuthorizationInterceptor(HEADERS))
                .build()
        ).build()

    @Singleton
    @Provides
    fun provideAppDatabase(
        application: Application,
        @Named("dbname") DB_NAME: String,
    ): AppDatabase = Room.databaseBuilder(
        application,
        AppDatabase::class.java,
        DB_NAME
    ).build()
}