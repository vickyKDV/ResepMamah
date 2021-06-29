package com.vickykdv.resepmamah.network.repository

import androidx.lifecycle.MutableLiveData
import androidx.paging.PagedList
import com.vickykdv.resepmamah.model.ResultsItem
import com.vickykdv.resepmamah.model.ResultsItemCategory
import com.vickykdv.resepmamah.states.ArticleState
import com.vickykdv.resepmamah.states.CategoryRecipeState
import com.vickykdv.resepmamah.states.DetailRecipeState
import com.vickykdv.resepmamah.states.RecipesState
import io.reactivex.disposables.CompositeDisposable


interface Repository {

    fun getCategory(callback : MutableLiveData<CategoryRecipeState>)
    fun getArticle(callback : MutableLiveData<ArticleState>)
    fun getRecipe(callback : MutableLiveData<RecipesState>)
    fun searchRecipe(find:String,callback : MutableLiveData<RecipesState>)
    fun getDetailRecipes(keyname:String, callback: MutableLiveData<DetailRecipeState>)

    fun getRecipes(
        callback: MutableLiveData<RecipesState>,
        data : MutableLiveData<PagedList<ResultsItem>>
    )





    fun getDisposible() : CompositeDisposable
}