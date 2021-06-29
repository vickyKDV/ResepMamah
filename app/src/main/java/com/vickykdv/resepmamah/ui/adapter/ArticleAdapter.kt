package com.vickykdv.mymovie.ui.movie.adapter

import android.R
import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.vickykdv.resepmamah.databinding.ItemListArticleBinding
import com.vickykdv.resepmamah.model.ResultsItemArticle
import java.util.*
import kotlin.collections.ArrayList


class ArticleAdapter(private val showDetail: (ResultsItemArticle) -> Unit) : RecyclerView.Adapter<ArticleAdapter.ViewHolder>() {

    private var data = ArrayList<ResultsItemArticle>()

    fun setData(movieList: List<ResultsItemArticle>?) {
        if (movieList.isNullOrEmpty()) return
        data.clear()
        data.addAll(movieList)
        notifyDataSetChanged()
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        with(holder.view){
            txtTitle.text = data[position].title
            holder.itemView.also {
//                Glide.with(it.context)
//                        .load( randomString)
//                        .diskCacheStrategy(DiskCacheStrategy.ALL)
//                        .into(imgThumb)

            }
        }
    }

    override fun getItemCount(): Int = data.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
            ItemListArticleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    class ViewHolder(val view: ItemListArticleBinding) : RecyclerView.ViewHolder(view.root)

}