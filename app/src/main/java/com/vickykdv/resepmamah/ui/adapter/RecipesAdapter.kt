package com.vickykdv.resepmamah.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.vickykdv.resepmamah.databinding.ItemListRecipesBinding
import com.vickykdv.resepmamah.databinding.ItemListTodayBinding
import com.vickykdv.resepmamah.model.ResultsItem

class RecipesAdapter(private val showDetail : (ResultsItem) -> Unit) : PagedListAdapter<ResultsItem,RecipesAdapter.ViewHolder>(DIFF_CALLBACK) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
            ItemListRecipesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder.view){
            txtTitle.text = getItem(position)?.title
            txtPortion.text = getItem(position)?.portion
            txtTiming.text = getItem(position)?.times
            txtDificulty.text = getItem(position)?.dificulty

            holder.itemView.also {
                Glide.with(it.context)
                        .load( getItem(position)?.thumb)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(imgThumb)
            }

            root.setOnClickListener {
                getItem(position)?.let { it1 -> showDetail(it1) }
            }
        }
    }

    companion object{
        private val DIFF_CALLBACK = object: DiffUtil.ItemCallback<ResultsItem>(){
            override fun areContentsTheSame(oldItem: ResultsItem, newItem: ResultsItem): Boolean {
                return oldItem.key == newItem.key
            }

            override fun areItemsTheSame(oldItem: ResultsItem, newItem: ResultsItem): Boolean {
                return oldItem == newItem
            }
        }
    }

    class ViewHolder(val view: ItemListRecipesBinding) : RecyclerView.ViewHolder(view.root)


}

