package com.vickykdv.resepmamah.network.repository

import androidx.lifecycle.MutableLiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.vickykdv.resepmamah.model.ResultsItem
import com.vickykdv.resepmamah.network.factory.Factory
import com.vickykdv.resepmamah.states.ArticleState
import com.vickykdv.resepmamah.states.CategoryRecipeState
import com.vickykdv.resepmamah.states.DetailRecipeState
import com.vickykdv.resepmamah.states.RecipesState
import io.reactivex.disposables.CompositeDisposable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class RemoteRepository @Inject constructor(
    private val apiService: ApiService,
    private val config : PagedList.Config,
    private val factory: Factory
) : Repository {

    private val disposable by lazy {
        CompositeDisposable()
    }

//    var disposable: CompositeDisposable =

    override fun getDisposible(): CompositeDisposable = disposable
    override fun getCategory(callback: MutableLiveData<CategoryRecipeState>) {
        apiService.getCategory()
                .map<CategoryRecipeState>(CategoryRecipeState::Result)
                .onErrorReturn(CategoryRecipeState::Error)
                .toFlowable()
                .startWith(CategoryRecipeState.Loading)
                .subscribe(callback::postValue)
                .let { return@let disposable::add }
    }

    override fun getArticle(callback: MutableLiveData<ArticleState>) {
        apiService.getArticle()
                .map<ArticleState>(ArticleState::Result)
                .onErrorReturn(ArticleState::Error)
                .toFlowable()
                .startWith(ArticleState.Loading)
                .subscribe(callback::postValue)
                .let { return@let disposable::add }
    }


    override fun getRecipe(callback: MutableLiveData<RecipesState>) {
        apiService.getRecipe("10")
                .map<RecipesState>(RecipesState::Result)
                .onErrorReturn(RecipesState::Error)
                .toFlowable()
                .startWith(RecipesState.Loading)
                .subscribe(callback::postValue)
                .let { return@let disposable::add }
    }

    override fun searchRecipe(find: String, callback: MutableLiveData<RecipesState>) {
        apiService.searchReceipe(find = find)
            .map<RecipesState>(RecipesState::Result)
            .onErrorReturn(RecipesState::Error)
            .toFlowable()
            .startWith(RecipesState.Loading)
            .subscribe(callback::postValue)
            .let { return@let disposable::add }
    }

    override fun getRecipes(
        callback: MutableLiveData<RecipesState>,
        data: MutableLiveData<PagedList<ResultsItem>>
    ) {
        CoroutineScope(Dispatchers.Main).launch {
            LivePagedListBuilder(
                factory.recipesDataFactory.also {
                    it.liveData = callback
                },
                config
            ).build().observeForever(data::postValue)
        }
    }

    override fun getDetailRecipes(
        keyname:String,
        callback: MutableLiveData<DetailRecipeState>
    ) {
       apiService.getDetailRecipe(keyname)
           .map<DetailRecipeState>(DetailRecipeState::Result)
           .onErrorReturn(DetailRecipeState::Error)
           .toFlowable()
           .startWith(DetailRecipeState.Loading)
           .subscribe(callback::postValue)
           .let {
               return@let disposable::add
           }
    }




}

