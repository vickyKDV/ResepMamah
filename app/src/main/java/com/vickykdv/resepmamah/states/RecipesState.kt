package com.vickykdv.resepmamah.states

import com.vickykdv.resepmamah.model.ResponseRecipes

sealed class RecipesState{
    object Loading:RecipesState()
    object LoadMore:RecipesState()
    data class Result(val data : ResponseRecipes) :RecipesState()
    data class Error(val error : Throwable) :RecipesState()
}