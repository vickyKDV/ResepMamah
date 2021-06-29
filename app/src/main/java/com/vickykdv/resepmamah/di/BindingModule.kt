package com.vickykdv.resepmamah.di

import com.vickykdv.resepmamah.network.repository.DataRepository
import com.vickykdv.resepmamah.network.repository.Repository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent

@Module
@InstallIn(ApplicationComponent::class)
abstract class BindingModule {
    @Binds
    abstract fun bindRepository(
        dataRepository: DataRepository
    ): Repository
}