package com.vickykdv.resepmamah.ui.searchrecipes

import android.widget.EditText
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jakewharton.rxbinding3.widget.TextViewTextChangeEvent
import com.jakewharton.rxbinding3.widget.textChangeEvents
import com.vickykdv.resepmamah.model.ResultsItem
import com.vickykdv.resepmamah.network.repository.Repository
import com.vickykdv.resepmamah.states.RecipesState
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class SearchRecipesViewModel @ViewModelInject constructor( private val repository: Repository
) : ViewModel() {

    val state : MutableLiveData<RecipesState> by lazy {
        MutableLiveData<RecipesState>()
    }

//    fun getRecipes(find:String) {
//        repository.searchRecipe(find,state)
//    }

    fun setupSearchMovie(editText: EditText){
        editText.textChangeEvents()
            .skipInitialValue()
            .debounce(500, TimeUnit.MILLISECONDS)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : DisposableObserver<TextViewTextChangeEvent>() {
                override fun onNext(t: TextViewTextChangeEvent) {
                    val find = t.text.toString()
                    if(find.length > 3) {
                        repository.searchRecipe(find, state)
                    }
                }

                override fun onError(e: Throwable) {

                }

                override fun onComplete() {

                }
            })
            .let { return@let repository.getDisposible()::add }
    }
}