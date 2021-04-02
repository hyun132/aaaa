package com.example.locationfinder.ui.setting

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.locationfinder.databinding.SettingItemBinding

/**
 * SettingRecyclerviewAdapter
 */
class SettingRecyclerviewAdapter :
    RecyclerView.Adapter<SettingRecyclerviewAdapter.SettingItemViewHolder>() {
    private val differCallback = object : DiffUtil.ItemCallback<SettingMenuItem>() {
        override fun areItemsTheSame(oldItem: SettingMenuItem, newItem: SettingMenuItem): Boolean {
            return oldItem.menuTitle == newItem.menuTitle
        }

        override fun areContentsTheSame(
            oldItem: SettingMenuItem,
            newItem: SettingMenuItem
        ): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SettingItemViewHolder {
        val binding = SettingItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SettingItemViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: SettingItemViewHolder,
        position: Int
    ) {
        val item = differ.currentList[position]
        holder.itemView.setOnClickListener {
            onItemClickListener?.let { it(item) }
        }
        holder.bind(item)
    }

    override fun getItemCount(): Int = differ.currentList.size

    class SettingItemViewHolder(private var binding: SettingItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(menuItem: SettingMenuItem) {
            binding.settingMenuItem = menuItem
            binding.executePendingBindings()
        }
    }

    private var onItemClickListener: ((SettingMenuItem) -> Unit)? = null

    fun setOnItemClickListener(listener: (SettingMenuItem) -> Unit) {
        onItemClickListener = listener
    }
}