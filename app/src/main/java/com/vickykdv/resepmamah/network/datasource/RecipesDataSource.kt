package com.vickykdv.resepmamah.network.datasource

import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.vickykdv.resepmamah.model.ResponseRecipes
import com.vickykdv.resepmamah.model.ResultsItem
import com.vickykdv.resepmamah.network.repository.ApiService
import com.vickykdv.resepmamah.states.RecipesState
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RecipesDataSource @Inject constructor(
    private val apiService: ApiService
): PageKeyedDataSource<Int, ResultsItem>()
{
    lateinit var liveData: MutableLiveData<RecipesState>


    private val disposable by lazy {
        CompositeDisposable()
    }





    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, ResultsItem>
    ) {
        apiService.getRecipes(1)
            .map<RecipesState>{
                callback.onResult(it.results.toMutableList(),1,2)
                RecipesState.Result(it)
            }
            .onErrorReturn(RecipesState::Error)
            .toFlowable()
            .startWith(RecipesState.Loading)
            .subscribe(liveData::postValue)
            .let { return@let disposable::add }
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, ResultsItem>) {
        liveData.postValue(RecipesState.LoadMore)
        apiService.getRecipes(params.key)
            .map <RecipesState>{
                callback.onResult(it.results.toMutableList(),params.key + 1)
                RecipesState.Result(it)
            }
            .onErrorReturn(RecipesState::Error)
            .toFlowable()
            .subscribe(liveData::postValue)
            .let { return@let disposable::add }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, ResultsItem>) {
    }

}