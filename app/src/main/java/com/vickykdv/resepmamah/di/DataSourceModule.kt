package com.vickykdv.resepmamah.di

import com.vickykdv.resepmamah.network.repository.ApiService
import com.vickykdv.resepmamah.network.datasource.RecipesDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
class DataSourceModule {

    @Provides
    @Singleton
    fun recipeDataSource(
        apiService: ApiService
    ):RecipesDataSource = RecipesDataSource(apiService)

}