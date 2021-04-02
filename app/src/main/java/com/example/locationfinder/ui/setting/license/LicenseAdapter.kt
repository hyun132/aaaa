package com.example.locationfinder.ui.setting.license

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.locationfinder.databinding.LicenseItemBinding

/**
 * LicenseAdapter
 */
class LicenseAdapter(licenseData: ArrayList<LicenseFragment.LicenseData>) :
    RecyclerView.Adapter<LicenseAdapter.LicenseViewHolder>() {
    private var licenseDataList = licenseData

    /**
     * LicenseViewHolder
     */
    class LicenseViewHolder(_binding: LicenseItemBinding) : RecyclerView.ViewHolder(_binding.root) {
        val binding = _binding
        fun bind(item: LicenseFragment.LicenseData) {
            binding.licenseData = item
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): LicenseViewHolder {
        val binding = LicenseItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LicenseViewHolder(binding)
    }

    override fun onBindViewHolder(holder: LicenseViewHolder, position: Int) {
        holder.bind(licenseDataList[position])
    }

    override fun getItemCount(): Int {
        return licenseDataList.size
    }
}