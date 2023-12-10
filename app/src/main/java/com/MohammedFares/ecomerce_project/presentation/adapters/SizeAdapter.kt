package com.MohammedFares.ecomerce_project.presentation.adapters

import android.content.Context
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.MohammedFares.ecomerce_project.R
import com.MohammedFares.ecomerce_project.databinding.ProductSizeItemBinding
import com.MohammedFares.ecomerce_project.domain.model.SelectebleSize

class SizeAdapter(val ctx: Context, val action: (id: Long) -> Unit) :
    ListAdapter<SelectebleSize, SizeAdapter.SizeViewHolder>(ClientSizeDiffCallback()) {

    class SizeViewHolder(val binding: ProductSizeItemBinding) : ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SizeViewHolder {
        val bind =
            ProductSizeItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SizeViewHolder(bind)
    }

    override fun onBindViewHolder(holder: SizeViewHolder, position: Int) {
        val size = getItem(position)

        holder.binding.sizeName.text = size.size.sizeName

        if (size.isSelected) {
            holder.binding.sizeContainer.setBackgroundResource(R.drawable.dark_bg)
            holder.binding.sizeName.setTextColor(ctx.getResources().getColor(R.color.white))
        }

        holder.binding.root.setOnClickListener {
            action(size.size.sizeId)
        }

    }

    class ClientSizeDiffCallback : DiffUtil.ItemCallback<SelectebleSize>() {
        override fun areItemsTheSame(oldItem: SelectebleSize, newItem: SelectebleSize): Boolean {
            //Log.d("ahahhhhhh", newItem.toString())
            //Log.d("ahahhhhhh", oldItem.toString())
            //return oldItem.size.sizeId == newItem.size.sizeId
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: SelectebleSize, newItem: SelectebleSize): Boolean {
            return oldItem.isSelected == newItem.isSelected
        }

    }
}