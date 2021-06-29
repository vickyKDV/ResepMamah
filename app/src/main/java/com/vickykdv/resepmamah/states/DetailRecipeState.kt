package com.vickykdv.resepmamah.states

import com.vickykdv.resepmamah.model.ResponseDetailRecipe
import com.vickykdv.resepmamah.model.ResponseRecipes


sealed class DetailRecipeState{
    object Loading:DetailRecipeState()
    data class Result(val data : ResponseDetailRecipe) :DetailRecipeState()
    data class Error(val error : Throwable) :DetailRecipeState()
}
