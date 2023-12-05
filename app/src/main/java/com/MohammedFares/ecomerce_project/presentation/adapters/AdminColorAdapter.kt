package com.MohammedFares.ecomerce_project.presentation.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.MohammedFares.ecomerce_project.data.entity.ProductColor
import com.MohammedFares.ecomerce_project.databinding.AdminColorItemBinding

class AdminColorAdapter() : ListAdapter<ProductColor, AdminColorAdapter.AdminColorViewHolder>(ColorDiffCallback()) {

    class AdminColorViewHolder(val binding: AdminColorItemBinding): ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdminColorViewHolder {
        val bind = AdminColorItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return AdminColorViewHolder(bind)
    }

    override fun onBindViewHolder(holder: AdminColorViewHolder, position: Int) {
        val color = getItem(position)

        holder.binding.colorName.text = color.colorName
        try {
            holder.binding.colorHex.setBackgroundColor(Color.parseColor(color.colorHexCode))
        } catch (e: Exception) {
            holder.binding.colorHex.setBackgroundColor(Color.parseColor("#FFFFFFFF"))
        }
    }
    class ColorDiffCallback : DiffUtil.ItemCallback<ProductColor>() {
        override fun areItemsTheSame(oldItem: ProductColor, newItem: ProductColor): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: ProductColor, newItem: ProductColor): Boolean {
            return oldItem.colorHexCode == newItem.colorHexCode
        }

    }
}