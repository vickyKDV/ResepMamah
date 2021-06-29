package com.vickykdv.resepmamah.di

import androidx.paging.PagedList
import com.vickykdv.resepmamah.network.repository.ApiService
import com.vickykdv.resepmamah.network.repository.DataRepository
import com.vickykdv.resepmamah.network.factory.Factory
import com.vickykdv.resepmamah.network.repository.RemoteRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
class RepositoryModule {

    @Singleton
    @Provides
    fun providesRemoteRepository(
        apiService: ApiService,
        config: PagedList.Config,
        factory: Factory
    ): RemoteRepository = RemoteRepository(
        apiService, config, factory
    )

    @Singleton
    @Provides
    fun provideDataRepository(
        remoteRepository: RemoteRepository
    ): DataRepository = DataRepository(remoteRepository)
}