package site.chenc.study_compose.di

import androidx.compose.material3.SnackbarHostState
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideSnackBarHostState(): SnackbarHostState {
        return SnackbarHostState()
    }
}