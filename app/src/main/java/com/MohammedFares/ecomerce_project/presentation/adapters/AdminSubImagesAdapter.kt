package com.MohammedFares.ecomerce_project.presentation.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.MohammedFares.ecomerce_project.R
import com.MohammedFares.ecomerce_project.data.entity.ProductColor
import com.MohammedFares.ecomerce_project.data.entity.ProductSize
import com.MohammedFares.ecomerce_project.data.entity.ProductSubImage
import com.MohammedFares.ecomerce_project.databinding.AdminProductImageItemBinding
import com.MohammedFares.ecomerce_project.databinding.AdminSizeItemBinding
import com.squareup.picasso.Picasso

class AdminSubImagesAdapter(var onClickImage: (image: ProductSubImage) -> Unit = {}, var onLongClickImage: (image: ProductSubImage) -> Unit = {}): ListAdapter<ProductSubImage, AdminSubImagesAdapter.AdminSubImageViewHolder>(SubImagesDiffCallback()) {

    class AdminSubImageViewHolder(val binding: AdminProductImageItemBinding): ViewHolder(binding.root)


    class SubImagesDiffCallback : DiffUtil.ItemCallback<ProductSubImage>() {

        override fun areItemsTheSame(oldItem: ProductSubImage, newItem: ProductSubImage): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(
            oldItem: ProductSubImage,
            newItem: ProductSubImage
        ): Boolean {
            return oldItem.imageUrl == newItem.imageUrl && oldItem.imageId == newItem.imageId
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdminSubImageViewHolder {
        val bind = AdminProductImageItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return AdminSubImageViewHolder(bind)
    }

    override fun onBindViewHolder(holder: AdminSubImageViewHolder, position: Int) {
        val image = getItem(position).imageUrl

        holder.binding.root.setOnClickListener {
            onClickImage(getItem(position))
        }
        holder.binding.root.setOnLongClickListener {
            onLongClickImage(getItem(position))
            return@setOnLongClickListener true
        }

        Picasso.get().load(image).resize(150,200).placeholder(R.drawable.no_connectio_50).into(holder.binding.productSubImage)
    }
}