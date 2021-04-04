package com.example.locationfinder.ui.location

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.locationfinder.R
import com.example.locationfinder.databinding.LocationItemBinding
import com.example.locationfinder.db.McItemEntity

/**
 * LocationRecyclerviewAdapter
 */
class LocationRecyclerviewAdapter :
    PagedListAdapter<McItemEntity, LocationRecyclerviewAdapter.LocationItemViewHolder>(
        differCallback
    ) {

    interface LikedClick {
        fun onClick(view: View, item:McItemEntity)
    }

    var likedClick: LikedClick? = null

    companion object {
        private val differCallback = object : DiffUtil.ItemCallback<McItemEntity>() {
            override fun areItemsTheSame(oldItem: McItemEntity, newItem: McItemEntity): Boolean {
                Log.d("log : ", "ItemChanged $newItem")
                return (oldItem.id == newItem.id)
            }

            override fun areContentsTheSame(oldItem: McItemEntity, newItem: McItemEntity): Boolean {
                return oldItem == newItem
            }
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): LocationItemViewHolder {
        val binding =
            LocationItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LocationItemViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: LocationItemViewHolder,
        position: Int
    ) {
        val item = differ.currentList[position]
        item?.let {
            holder.itemView.setOnClickListener {
                onItemClickListener?.let { it(item) }
            }
            holder.itemView.findViewById<ImageView>(R.id.iv_liked).setOnClickListener { v ->
                likedClick?.onClick(v, item)
            }
            holder.bind(item)
        }

    }

    override fun getItemCount(): Int = differ.currentList.size

    class LocationItemViewHolder(private var binding: LocationItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(mcItemEntity: McItemEntity) {
            binding.mcItemEntity = mcItemEntity
            binding.executePendingBindings() // binding에 필요한 모든 작업들 즉시 실행하도록 강제하는..?
        }
    }

    private var onItemClickListener: ((McItemEntity) -> Unit)? = null

    fun setOnItemClickListener(listener: (McItemEntity) -> Unit) {
        onItemClickListener = listener
    }
}