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
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DataRepository @Inject constructor(
    private val remoteRepository: RemoteRepository
) : Repository
{

    override fun getCategory(callback: MutableLiveData<CategoryRecipeState>) {
        remoteRepository.getCategory(callback)
    }

    override fun getArticle(callback: MutableLiveData<ArticleState>) {
        remoteRepository.getArticle(callback)
    }

    override fun getRecipe(callback: MutableLiveData<RecipesState>) {
        remoteRepository.getRecipe(callback)
    }

    override fun searchRecipe(find: String, callback: MutableLiveData<RecipesState>) {
        remoteRepository.searchRecipe(find,callback)
    }

    override fun getRecipes(
        callback: MutableLiveData<RecipesState>,
        data: MutableLiveData<PagedList<ResultsItem>>
    ) {
       remoteRepository.getRecipes(callback,data)
    }


    override fun getDetailRecipes(
        keyname: String,
        callback: MutableLiveData<DetailRecipeState>) {
       remoteRepository.getDetailRecipes(keyname,callback)
    }

    override fun getDisposible(): CompositeDisposable {
        return remoteRepository.getDisposible()
    }

}