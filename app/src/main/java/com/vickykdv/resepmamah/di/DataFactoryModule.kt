package com.vickykdv.resepmamah.di

import com.vickykdv.resepmamah.network.factory.Factory
import com.vickykdv.resepmamah.network.factory.RecipesDataFactory
import com.vickykdv.resepmamah.network.datasource.RecipesDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
class DataFactoryModule {

    @Provides
    @Singleton
    fun providesFactory(
        recipesDataFactory: RecipesDataFactory
    ):Factory = Factory(recipesDataFactory)

    @Provides
    @Singleton
    fun providesRecipesFactory(
        recipesDataSource: RecipesDataSource
    ):RecipesDataFactory = RecipesDataFactory(recipesDataSource)
}