package com.MohammedFares.ecomerce_project.presentation.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.MohammedFares.ecomerce_project.data.entity.ProductColor
import com.MohammedFares.ecomerce_project.data.entity.ProductSize
import com.MohammedFares.ecomerce_project.data.entity.ProductSubImage
import com.MohammedFares.ecomerce_project.databinding.AdminSizeItemBinding

class AdminSizesAdapter(
    var onClickSize: (size: ProductSize) -> Unit = {},
    var onLongClickSize: (size: ProductSize) -> Unit = {}
) : ListAdapter<ProductSize, AdminSizesAdapter.AdminSizesViewHolder>(SizeDiffCallback()) {

    class AdminSizesViewHolder(val binding: AdminSizeItemBinding) : ViewHolder(binding.root)


    class SizeDiffCallback : DiffUtil.ItemCallback<ProductSize>() {

        override fun areItemsTheSame(oldItem: ProductSize, newItem: ProductSize): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: ProductSize, newItem: ProductSize): Boolean {
            return oldItem.sizeName == newItem.sizeName
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdminSizesViewHolder {
        val bind = AdminSizeItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AdminSizesViewHolder(bind)
    }

    override fun onBindViewHolder(holder: AdminSizesViewHolder, position: Int) {
        val size = getItem(position)

        holder.binding.sizeName.text = size.sizeName

        holder.binding.root.setOnClickListener {
            onClickSize(size)
        }
    }
}