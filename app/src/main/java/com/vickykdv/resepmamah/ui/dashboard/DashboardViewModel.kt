package com.vickykdv.resepmamah.ui.dashboard

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.vickykdv.resepmamah.network.repository.Repository
import com.vickykdv.resepmamah.states.ArticleState
import com.vickykdv.resepmamah.states.CategoryRecipeState
import com.vickykdv.resepmamah.states.RecipesState

class DashboardViewModel @ViewModelInject constructor(
        private val repository: Repository
):ViewModel() {

    val stateCategory : MutableLiveData<CategoryRecipeState> by lazy {
        MutableLiveData<CategoryRecipeState>()
    }

    val stateListRecipe : MutableLiveData<RecipesState> by lazy {
        MutableLiveData<RecipesState>()
    }

    fun getCategory(){
        repository.getCategory(stateCategory)
    }

    fun getRecipe(){
        repository.getRecipe(stateListRecipe)
    }
}