package com.vickykdv.resepmamah.network.factory

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
data class Factory @Inject constructor(
    val recipesDataFactory: RecipesDataFactory
)
