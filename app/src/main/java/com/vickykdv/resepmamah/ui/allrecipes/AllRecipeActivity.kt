package com.vickykdv.resepmamah.ui.allrecipes

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.vickykdv.resepmamah.databinding.ActivityAllRecipesBinding
import com.vickykdv.resepmamah.databinding.BottomsheetdialogBinding
import com.vickykdv.resepmamah.model.ResultsItem
import com.vickykdv.resepmamah.states.RecipesState
import com.vickykdv.resepmamah.ui.adapter.RecipesAdapter
import com.vickykdv.resepmamah.ui.detailRecipe.DetailRecipesActivity
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class AllRecipeActivity : AppCompatActivity() {

    private val viewModel : RecipesAllViewModel by viewModels()
    private val binding : ActivityAllRecipesBinding by lazy {
        ActivityAllRecipesBinding.inflate(layoutInflater)
    }

    private val recipesAdapter : RecipesAdapter by lazy {
        RecipesAdapter { item -> recipeItem(item)}
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupStatusBar()
        setupView()
        getData()
        setupViewModel()
    }

    private fun getData(){
        viewModel.getRecipes()
    }

    private fun setupViewModel(){
        viewModel.state.observe(this, {
            when (it) {
                is RecipesState.Loading -> {
                    getLoadingRecipe(true)
                    getLoadMoreRecipe(false)
                }
                is RecipesState.Result -> {
                    getLoadingRecipe(false)
                    getLoadMoreRecipe(false)
                }
                is RecipesState.LoadMore -> {
                    getLoadingRecipe(false)
                    getLoadMoreRecipe(true)
                }
                is RecipesState.Error -> showError()
            }
        })

        viewModel.data.observe(this, Observer(recipesAdapter::submitList))


    }



    private fun showError() {
        val binding = BottomsheetdialogBinding.inflate(layoutInflater)
        val dialog = BottomSheetDialog(this)
        dialog.setContentView(binding.root)
        with(binding) {
            btnOk.setOnClickListener {
                dialog.dismiss()
            }
        }

        dialog.show()
    }

    private fun getLoadingRecipe(loading: Boolean) {
        with(binding) {
            if (loading) {
                rv.visibility = View.INVISIBLE
                pb.visibility = View.VISIBLE
            }else {
                rv.visibility = View.VISIBLE
                pb.visibility = View.INVISIBLE
            }
        }
    }

    private fun getLoadMoreRecipe(loading: Boolean) {
        with(binding) {
            if (loading) {
                pbLoadMore.visibility = View.VISIBLE
            }else {
                pbLoadMore.visibility = View.GONE
            }
        }
    }


    private fun setupView() {
        with(binding) {
            rv.also {
                it.adapter = recipesAdapter
                it.layoutManager = LinearLayoutManager(
                    this@AllRecipeActivity, LinearLayoutManager.VERTICAL, false
                )
                it.setHasFixedSize(true)
            }

            imgBack.setOnClickListener { finish() }
        }
    }

    private fun recipeItem(item: ResultsItem) {
        startActivity(Intent(this, DetailRecipesActivity::class.java).also {
            it.putExtra("data", item)
        })
        Log.d("viewModel", "detailRecipes: ${item.title}");
    }


    private fun setupStatusBar() {
        with(window){
            clearFlags(
                WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                        or WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION)
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        or View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR)
            }
            addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            navigationBarColor = ContextCompat.getColor(this@AllRecipeActivity, android.R.color.white)
            statusBarColor = ContextCompat.getColor(this@AllRecipeActivity, android.R.color.white)
        }
    }

}