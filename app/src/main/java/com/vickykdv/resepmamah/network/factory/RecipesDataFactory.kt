package com.vickykdv.resepmamah.network.factory

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.vickykdv.resepmamah.model.ResultsItem
import com.vickykdv.resepmamah.network.datasource.RecipesDataSource
import com.vickykdv.resepmamah.states.RecipesState
import javax.inject.Inject

class RecipesDataFactory @Inject constructor(
    private val recipesDataSource: RecipesDataSource
) : DataSource.Factory<Int,ResultsItem>()
{
    lateinit var liveData: MutableLiveData<RecipesState>

    override fun create(): DataSource<Int, ResultsItem> {
        return recipesDataSource.also {
            it.liveData= liveData
        }
    }
}