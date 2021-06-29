package com.vickykdv.resepmamah.ui.dashboard

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.SnapHelper
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.vickykdv.mymovie.ui.movie.adapter.CategoryAdapter
import com.vickykdv.mymovie.ui.movie.adapter.RecipeAdapter
import com.vickykdv.resepmamah.databinding.ActivityMainBinding
import com.vickykdv.resepmamah.databinding.BottomsheetdialogBinding
import com.vickykdv.resepmamah.model.ResultsItem
import com.vickykdv.resepmamah.model.ResultsItemCategory
import com.vickykdv.resepmamah.states.CategoryRecipeState
import com.vickykdv.resepmamah.states.RecipesState
import com.vickykdv.resepmamah.ui.allrecipes.AllRecipeActivity
import com.vickykdv.resepmamah.ui.detailRecipe.DetailRecipesActivity
import com.vickykdv.resepmamah.ui.searchrecipes.SearchRecipesActivity
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class DashboardActivity : AppCompatActivity() {

//    private val allViewModel : RecipesAllViewModel by viewModels()
    private val viewModel : DashboardViewModel by viewModels()

    private val binding : ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val recipesAdapter : RecipeAdapter by lazy {
        RecipeAdapter { item -> recipeItem(item)}
    }

    private val categoryAdapter : CategoryAdapter by lazy {
        CategoryAdapter{ item -> categoryItem(item)}
    }

    private fun categoryItem(item: ResultsItemCategory) {
        Log.d("allViewModel", "categoryItem: $item")
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
        viewModel.getCategory()
        viewModel.getRecipe()
    }

    private fun setupViewModel(){
        viewModel.stateCategory.observe(this, {
            when (it) {
                is CategoryRecipeState.Loading -> getLoadingCategory(true)
                is CategoryRecipeState.Result -> successGetDataCategory(it.data.results)
                is CategoryRecipeState.Error -> showError()
            }
        })

        viewModel.stateListRecipe.observe(this, {
            when (it) {
                is RecipesState.Loading -> getLoadingRecipe(true)
                is RecipesState.Result -> successGetDataRecipe(it.data.results)
                is RecipesState.Error -> showError()
            }
        })
    }

    private fun successGetDataCategory(data: List<ResultsItemCategory>) {
        getLoadingCategory(false)
        categoryAdapter.setData(data)
    }

    private fun successGetDataRecipe(data: List<ResultsItem>) {
        getLoadingRecipe(false)
        recipesAdapter.setData(data)
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
                lnArticle.visibility = View.INVISIBLE
                pbArticle.visibility = View.VISIBLE
            }else {
                lnArticle.visibility = View.VISIBLE
                pbArticle.visibility = View.INVISIBLE
            }
        }
    }

    private fun getLoadingCategory(loading: Boolean) {
        with(binding) {
            if (loading) {
                lnCategory.visibility = View.INVISIBLE
                pbCategory.visibility = View.VISIBLE
            }else {
                lnCategory.visibility = View.VISIBLE
                pbCategory.visibility = View.INVISIBLE
            }
        }
    }

    private fun setupView() {
        with(binding) {
            rvRecipes.also {
                it.adapter = recipesAdapter
                it.layoutManager = LinearLayoutManager(
                        this@DashboardActivity, LinearLayoutManager.VERTICAL, false
                )
                it.setHasFixedSize(true)
            }

            rvCategory.also {
                it.adapter = categoryAdapter
                it.layoutManager = LinearLayoutManager(
                        this@DashboardActivity, LinearLayoutManager.HORIZONTAL, false
                )
                it.setHasFixedSize(true)
            }

            val helper: SnapHelper = LinearSnapHelper()
            helper.attachToRecyclerView(rvCategory)
            txtSeeAll.setOnClickListener {
                startActivity(Intent(this@DashboardActivity, AllRecipeActivity::class.java))
            }

            lnSearch.setOnClickListener {
                startActivity(Intent(this@DashboardActivity, SearchRecipesActivity::class.java))
            }


//            imgBack.setOnClickListener { finish() }
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
            clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                    or WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION)
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        or View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR)
            }
            addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            navigationBarColor = ContextCompat.getColor(this@DashboardActivity, android.R.color.white)
            statusBarColor = ContextCompat.getColor(this@DashboardActivity, android.R.color.white)
        }
    }

}