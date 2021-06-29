package com.vickykdv.resepmamah.ui.searchrecipes

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.EditText
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.core.widget.doAfterTextChanged
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.vickykdv.mymovie.ui.movie.adapter.RecipeAdapter
import com.vickykdv.resepmamah.R
import com.vickykdv.resepmamah.databinding.ActivityAllRecipesBinding
import com.vickykdv.resepmamah.databinding.ActivitySearchRecipesBinding
import com.vickykdv.resepmamah.databinding.BottomsheetdialogBinding
import com.vickykdv.resepmamah.model.ResultsItem
import com.vickykdv.resepmamah.states.CategoryRecipeState
import com.vickykdv.resepmamah.states.RecipesState
import com.vickykdv.resepmamah.ui.adapter.RecipesAdapter
import com.vickykdv.resepmamah.ui.dashboard.DashboardViewModel
import com.vickykdv.resepmamah.ui.detailRecipe.DetailRecipesActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchRecipesActivity : AppCompatActivity() {

    private val viewModel : SearchRecipesViewModel by viewModels()


    private val binding : ActivitySearchRecipesBinding by lazy {
        ActivitySearchRecipesBinding.inflate(layoutInflater)
    }

    private val recipesAdapter : RecipeAdapter by lazy {
        RecipeAdapter { item -> recipeItem(item)}
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupStatusBar()
        setupView()
        setupViewModel()
    }



    private fun recipeItem(item: ResultsItem) {
        startActivity(Intent(this, DetailRecipesActivity::class.java).also {
            it.putExtra("data", item)
        })
        Log.d("viewModel", "detailRecipes: ${item.title}");
    }

    private fun setupViewModel(){
        viewModel.state.observe(this, {
            when (it) {
                is RecipesState.Loading -> getLoadingRecipe(true)
                is RecipesState.Result -> successGetDataRecipe(it.data.results)
                is RecipesState.Error -> showError()
            }
        })

        viewModel.setupSearchMovie(binding.edtSearch)
    }

    private fun successGetDataRecipe(data: List<ResultsItem>) {
        getLoadingRecipe(false)
        recipesAdapter.setData(data)
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

    private fun setupView() {
        with(binding) {
            rv.also {
                it.adapter = recipesAdapter
                it.layoutManager = LinearLayoutManager(
                    this@SearchRecipesActivity, LinearLayoutManager.VERTICAL, false
                )
                it.setHasFixedSize(true)
            }

//            edtSearch.afterTextChanged {
//                if(it.length > 2){
//                    Log.d("toolbar", "setupView: $it");
//                    viewModel.getRecipes(it)
//                }else{
//                    getLoadingRecipe(false)
//                }
//            }



            imgBack.setOnClickListener { finish() }
        }
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
            navigationBarColor = ContextCompat.getColor(this@SearchRecipesActivity, android.R.color.white)
            statusBarColor = ContextCompat.getColor(this@SearchRecipesActivity, android.R.color.white)
        }
    }


    /*Extention Afer Change*/
    private fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
        this.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(editable: Editable?) {
                afterTextChanged.invoke(editable.toString())
            }
        })
    }
}