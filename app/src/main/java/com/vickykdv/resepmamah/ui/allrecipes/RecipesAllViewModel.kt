package com.vickykdv.resepmamah.ui.allrecipes

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.vickykdv.resepmamah.model.ResultsItem
import com.vickykdv.resepmamah.network.repository.Repository
import com.vickykdv.resepmamah.states.RecipesState

class RecipesAllViewModel @ViewModelInject constructor(
    private val repository: Repository
) : ViewModel() {

    val state : MutableLiveData<RecipesState> by lazy {
        MutableLiveData<RecipesState>()
    }

    val data : MutableLiveData<PagedList<ResultsItem>> by lazy {
        MutableLiveData<PagedList<ResultsItem>>()
    }

    fun getRecipes() {
        repository.getRecipes(state, data)
    }
}