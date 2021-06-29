package com.vickykdv.resepmamah.states

import com.vickykdv.resepmamah.model.ResponseCategory
import com.vickykdv.resepmamah.model.ResponseDetailRecipe
import com.vickykdv.resepmamah.model.ResponseRecipes
import com.vickykdv.resepmamah.model.ResultsItemCategory


sealed class CategoryRecipeState{
    object Loading:CategoryRecipeState()
    data class Result(val data : ResponseCategory) :CategoryRecipeState()
    data class Error(val error : Throwable) :CategoryRecipeState()
}
