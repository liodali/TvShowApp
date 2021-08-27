package dali.hamza.tvshowapp.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.components.ViewModelComponent
import dali.hamza.core.repository.MovieRepository
import dali.hamza.domain.repository.IMovieRepository

@Module
@InstallIn(
    ActivityComponent::class,
    ViewModelComponent::class
)
object MovieModule {
    @Provides
    fun provideMovieRepository(repository: MovieRepository): IMovieRepository =
        repository
}