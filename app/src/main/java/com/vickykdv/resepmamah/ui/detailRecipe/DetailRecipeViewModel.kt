package com.vickykdv.resepmamah.ui.detailRecipe

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.vickykdv.resepmamah.model.ResultsItem
import com.vickykdv.resepmamah.network.repository.Repository
import com.vickykdv.resepmamah.states.DetailRecipeState

class DetailRecipeViewModel @ViewModelInject constructor(private val repository: Repository) : ViewModel() {

    val state : MutableLiveData<DetailRecipeState> by lazy {
        MutableLiveData<DetailRecipeState>()
    }

    val data : MutableLiveData<ResultsItem> by lazy {
        MutableLiveData<ResultsItem>()
    }

    fun getRecipes(keyName:String) {
        repository.getDetailRecipes(keyName, state)
    }
}