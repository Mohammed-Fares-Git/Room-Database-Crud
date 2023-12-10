package com.MohammedFares.ecomerce_project.presentation.adapters

import android.content.Context
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.MohammedFares.ecomerce_project.R
import com.MohammedFares.ecomerce_project.databinding.ProductSizeItemBinding
import com.MohammedFares.ecomerce_project.domain.model.SelectebleSize

class SizeAdapter2(val ctx: Context, val action: (id: Long) -> Unit) :
    RecyclerView.Adapter<SizeAdapter2.SizeViewHolder>() {

    private lateinit var sizes: List<SelectebleSize>

    class SizeViewHolder(val binding: ProductSizeItemBinding) : ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SizeViewHolder {
        val bind =
            ProductSizeItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SizeViewHolder(bind)
    }

    override fun getItemCount(): Int {
        return sizes.size
    }

    override fun onBindViewHolder(holder: SizeViewHolder, position: Int) {
        val size = sizes.get(position)

        holder.binding.sizeName.text = size.size.sizeName

        if (size.isSelected) {
            holder.binding.sizeContainer.setBackgroundResource(R.drawable.dark_bg)
            holder.binding.sizeName.setTextColor(ctx.getResources().getColor(R.color.white))
        } else {
            holder.binding.sizeContainer.setBackgroundResource(R.drawable.grey_bg)
            holder.binding.sizeName.setTextColor(ctx.getResources().getColor(R.color.black))
        }

        holder.binding.root.setOnClickListener {
            action(size.size.sizeId)
        }

    }

    fun setSizes(sizes: List<SelectebleSize>) {
        this.sizes = sizes
        notifyDataSetChanged()
    }


}