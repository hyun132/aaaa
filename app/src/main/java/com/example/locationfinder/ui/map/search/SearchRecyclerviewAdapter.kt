package com.example.locationfinder.ui.map.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.locationfinder.databinding.SearchItemBinding
import com.example.locationfinder.models.McItemDto

/**
 * LocationRecyclerviewAdapter
 */
class SearchRecyclerviewAdapter :
    RecyclerView.Adapter<SearchRecyclerviewAdapter.SearchItemViewHolder>() {
    private val differCallback = object : DiffUtil.ItemCallback<McItemDto>() {
        override fun areItemsTheSame(oldItem: McItemDto, newItem: McItemDto): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: McItemDto, newItem: McItemDto): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SearchItemViewHolder {
        val binding = SearchItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SearchItemViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: SearchItemViewHolder,
        position: Int
    ) {
        val item = differ.currentList[position]
        holder.itemView.setOnClickListener {
            onItemClickListener?.let { it(item) }
        }
        holder.bind(item)
    }

    override fun getItemCount(): Int = differ.currentList.size

    class SearchItemViewHolder(private var binding: SearchItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(mcItem: McItemDto) {
            binding.mcItem = mcItem
            binding.executePendingBindings()
        }
    }

    private var onItemClickListener: ((McItemDto) -> Unit)? = null

    fun setOnItemClickListener(listener: (McItemDto) -> Unit) {
        onItemClickListener = listener
    }
}