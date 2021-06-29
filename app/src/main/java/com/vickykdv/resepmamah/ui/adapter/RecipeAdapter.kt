package com.vickykdv.mymovie.ui.movie.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.vickykdv.resepmamah.databinding.ItemListRecipesBinding
import com.vickykdv.resepmamah.model.ResultsItem

class RecipeAdapter (
    private val showDetail: (ResultsItem) -> Unit
) : RecyclerView.Adapter<RecipeAdapter.ViewHolder>() {

    private var data = ArrayList<ResultsItem>()

    fun setData(list: List<ResultsItem>?) {
        if (list.isNullOrEmpty()) return
        data.clear()
        data.addAll(list)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder.view){
            txtTitle.text = data[position].title
            txtPortion.text = data[position].portion
            txtTiming.text = data[position].times
            txtDificulty.text = data[position].dificulty
            android.util.Log.d("data", "onBindViewHolder: ${data[position].times}")
            android.util.Log.d("data", "onBindViewHolder: ${data[position].dificulty}")
            android.util.Log.d("data", "onBindViewHolder: ${data[position].portion}")
            holder.itemView.also {
                Glide.with(it.context)
                        .load( data[position].thumb)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(imgThumb)

            }

            root.setOnClickListener {
                showDetail(data[position])
            }
        }
    }

    override fun getItemCount(): Int = data.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
        ItemListRecipesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    class ViewHolder(val view: ItemListRecipesBinding) : RecyclerView.ViewHolder(view.root)

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
}