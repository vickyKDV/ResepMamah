package com.vickykdv.resepmamah.ui.detailRecipe

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Typeface
import android.os.Bundle
import android.text.SpannableString
import android.util.Log
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.marginBottom
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.vickykdv.resepmamah.R
import com.vickykdv.resepmamah.Utils
import com.vickykdv.resepmamah.Utils.margin
import com.vickykdv.resepmamah.databinding.ActivityDetailRecipesBinding
import com.vickykdv.resepmamah.model.ResponseDetailRecipe
import com.vickykdv.resepmamah.model.Results
import com.vickykdv.resepmamah.model.ResultsItem
import com.vickykdv.resepmamah.states.DetailRecipeState
import dagger.hilt.android.AndroidEntryPoint
import kotlin.math.roundToInt


@AndroidEntryPoint
class DetailRecipesActivity : AppCompatActivity() {

    private val viewModel : DetailRecipeViewModel by viewModels()

    private val binding : ActivityDetailRecipesBinding by lazy {
        ActivityDetailRecipesBinding.inflate(layoutInflater)
    }

//    private val txt by lazy {
//
//    }

    private val data : ResultsItem? by lazy {
        intent.getParcelableExtra("data")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupView()
        setupViewModel()


    }

    private fun setupViewModel(){
        viewModel.state.observe(this, Observer {
            when (it) {
                is DetailRecipeState.Loading -> {
                    getLoadingRecipe(true)
                }
                is DetailRecipeState.Result -> {
                    getLoadingRecipe(false)
                    successDetailRecipe(it.data)
                }
                is DetailRecipeState.Error -> {
                    getLoadingRecipe(false)
                }
            }
        })

        viewModel.getRecipes(data?.key.toString())
    }

    private fun successDetailRecipe(it: ResponseDetailRecipe){
        with(binding){
            toolbarLayout.title = ""

            /*Load Image Header*/
            it.results?.thumb?.let { it1 -> loadImage(it1) }
//            Glide.with(this@DetailRecipesActivity)
//                .load(it.results?.thumb)
//                .diskCacheStrategy(DiskCacheStrategy.ALL)
//                .into(img)
            it.results?.let { results -> getAndCreateOther(results) }
        }
    }

    private fun loadImage(url:String){
        Glide.with(this@DetailRecipesActivity).asBitmap()
            .load(url)
            .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
            .listener(object : RequestListener<Bitmap?> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any,
                    target: Target<Bitmap?>,
                    isFirstResource: Boolean
                ): Boolean {
                    binding.shimImage.visibility = View.INVISIBLE
//                    binding.img.visibility = View.INVISIBLE
                    return false
                }

                override fun onResourceReady(
                    resource: Bitmap?,
                    model: Any,
                    target: Target<Bitmap?>,
                    dataSource: DataSource,
                    isFirstResource: Boolean
                ): Boolean {
                    binding.shimImage.visibility = View.INVISIBLE
//                    binding.img.visibility = View.VISIBLE
                    return false
                }
            })
            .into(binding.img)
    }

    @SuppressLint("SetTextI18n")
    private fun getAndCreateOther(results:Results){
        with(binding) {
            with(results) {
                val titles = title.toString()

                val level = dificulty.toString()

                when (level){
                    "Cukup rumit" ->
                        imgLevel.setImageResource(R.drawable.ic_baseline_sangat_sulit)
                    "Mudah" ->
                        imgLevel.setImageResource(R.drawable.ic_baseline_mudah)
                    else ->
                        imgLevel.setImageResource(R.drawable.ic_baseline_sulit)
                    }

                txtLevel.text = level

                txtPorsi.text = servings.toString()

                txtTime.text = times.toString()

                binding.toolbarLayout.title = title
                binding.toolbarLayout.isTitleEnabled = true
                val typefaceBold =
                    ResourcesCompat.getFont(this@DetailRecipesActivity, R.font.nunito_black)
                val typefaceNormal =
                    ResourcesCompat.getFont(this@DetailRecipesActivity, R.font.nunito)
                val sizeBig = resources.getDimension(R.dimen._10ssp)
                val sizeNormal = resources.getDimension(R.dimen._6ssp)

                /*Create TextView Title*/
                val txtTitle = TextView(this@DetailRecipesActivity)
                txtTitle.textSize = sizeBig
                txtTitle.text = titles
                txtTitle.typeface = typefaceBold
                ln.addView(txtTitle)
                txtTitle.margin(bottom = resources.getDimension(R.dimen._8sdp))

                /*Create TextView Description*/
                val txtDescription = TextView(this@DetailRecipesActivity)
                txtDescription.textSize = sizeNormal
                txtDescription.typeface = typefaceNormal
                txtDescription.text = desc.toString()

                ln.addView(txtDescription)
                txtDescription.margin(bottom = resources.getDimension(R.dimen._8sdp))


                /*Create TextView Title Bahan*/
                val txtTitleBahan = TextView(this@DetailRecipesActivity)
                txtTitleBahan.textSize = sizeNormal
                txtTitleBahan.typeface = typefaceBold
                txtTitleBahan.text = "■ Bahan - bahan :"
                ln.addView(txtTitleBahan)
                txtTitleBahan.margin(bottom = resources.getDimension(R.dimen._2sdp))

                /*Loop Bahan Bahan*/
                val bahanItr = (ingredient)?.iterator()
                bahanItr?.forEach {
                    /*Create TextView Bahan*/
                    val txtBahan = TextView(this@DetailRecipesActivity)
                    txtBahan.textSize = sizeNormal
                    txtBahan.typeface = typefaceNormal
                    txtBahan.textAlignment = TextView.TEXT_ALIGNMENT_TEXT_START
                    ln.addView(txtBahan)
                    txtBahan.margin(left = resources.getDimension(R.dimen._4sdp))
                    txtBahan.text = "   • ${it.toString()}"
                    if (!bahanItr.hasNext()) {
                        txtBahan.margin(bottom = resources.getDimension(R.dimen._8sdp))
                    }
                }


                /*Title Cara Buat*/
                val txtTitleCaraBuat = TextView(this@DetailRecipesActivity)
                txtTitleCaraBuat.textSize = sizeNormal
                txtTitleCaraBuat.typeface = typefaceBold
                txtTitleCaraBuat.text = "■ Cara membuat :"
                ln.addView(txtTitleCaraBuat)
                txtTitleCaraBuat.margin(bottom = resources.getDimension(R.dimen._2sdp))

                /*Loop Tata Cara Buat*/
                val tataCaraItr = (step)?.iterator()
                tataCaraItr?.forEach {
                    /*Create TextView Cara Buat*/
                    val txtCaraBuat = TextView(this@DetailRecipesActivity)
                    txtCaraBuat.textSize = sizeNormal
                    txtCaraBuat.typeface = typefaceNormal
                    txtCaraBuat.textAlignment = TextView.TEXT_ALIGNMENT_TEXT_START
                    ln.addView(txtCaraBuat)
                    txtCaraBuat.margin(left = resources.getDimension(R.dimen._4sdp))
                    txtCaraBuat.text = "• ${it.toString()}"
                    if (!tataCaraItr.hasNext()) {
                        txtCaraBuat.margin(bottom = resources.getDimension(R.dimen._24sdp))
                    }
                }
            }
        }
    }

    private fun setupView() {
        with(binding) {
            setSupportActionBar(toolbar)
            supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_ios_24_black)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.setDisplayShowTitleEnabled(false)

        }
    }



    private fun getLoadingRecipe(loading: Boolean) {
        with(binding) {
            if (loading) {
                shimContent.visibility  = View.VISIBLE
                lnData.visibility = View.INVISIBLE
            }else {
                shimContent.visibility  = View.INVISIBLE
                lnData.visibility = View.VISIBLE
            }
        }
    }
}