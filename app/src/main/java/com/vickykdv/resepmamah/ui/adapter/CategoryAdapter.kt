package com.vickykdv.mymovie.ui.movie.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.vickykdv.resepmamah.databinding.ItemListCategoryBinding
import com.vickykdv.resepmamah.model.ResultsItemCategory
import java.util.*
import kotlin.collections.ArrayList

class CategoryAdapter(
        private val showDetail: (ResultsItemCategory) -> Unit
) : RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {

    private var data = ArrayList<ResultsItemCategory>()

    fun setData(list: List<ResultsItemCategory>?) {
        if (list.isNullOrEmpty()) return
        data.clear()
        data.addAll(list)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val rnd = Random()
        val color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256))
        with(holder.view){
            txtTitle.text = data[position].category
            cv.setCardBackgroundColor(color)
        }
        
    }

    override fun getItemCount(): Int = data.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
            ItemListCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    class ViewHolder(val view: ItemListCategoryBinding) : RecyclerView.ViewHolder(view.root)

}