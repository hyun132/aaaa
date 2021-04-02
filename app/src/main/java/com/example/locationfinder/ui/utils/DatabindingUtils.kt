package com.example.locationfinder.ui.utils

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.locationfinder.R
import com.example.locationfinder.db.McItemEntity
import com.example.locationfinder.ui.location.LocationRecyclerviewAdapter

/**
 * DatabindingUtils
 */
object DatabindingUtils {
    @JvmStatic
    @BindingAdapter("listItem")
    fun bindSearchList(recyclerView: RecyclerView, items: List<McItemEntity>?) {
        var adapter: LocationRecyclerviewAdapter
        if (recyclerView.adapter == null) {
            adapter = LocationRecyclerviewAdapter()
            recyclerView.adapter = adapter
        } else {
            adapter = recyclerView.adapter as LocationRecyclerviewAdapter
        }
        adapter.differ.submitList(items)
    }

    @JvmStatic
    @BindingAdapter("liked_src")
    fun bindItemLiked(imageView: ImageView, isLiked: Boolean) {
        if (isLiked) {
            imageView.setImageResource(R.drawable.ic_yellow_star)
        } else {
            imageView.setImageResource(R.drawable.ic_gray_star)
        }
    }
}