package com.MohammedFares.ecomerce_project.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.MohammedFares.ecomerce_project.data.entity.Product
import com.MohammedFares.ecomerce_project.data.entity.ProductSubImage
import com.MohammedFares.ecomerce_project.data.relations.ProductWithDetails
import com.MohammedFares.ecomerce_project.databinding.ClientProductItemBinding
import com.squareup.picasso.Picasso

class ProductsAdapter (val action: (id: Long)->Unit = {}): ListAdapter<ProductWithDetails,ProductsAdapter.ProductsViewHolder>(ProductDiffCallback()) {
    inner class ProductsViewHolder(val binding: ClientProductItemBinding): ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductsViewHolder {
        val bind = ClientProductItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ProductsViewHolder(bind)
    }

    override fun onBindViewHolder(holder: ProductsViewHolder, position: Int) {
        val product = getItem(position)
        Picasso.get().load(product.product.mainImage).into(holder.binding.productImage)
        holder.binding.productName.text = product.product.productName
        holder.binding.productPrice.text = "${ product.product.price }"
        holder.binding.root.setOnClickListener {
            action(product.product.productId)
        }
    }


    class ProductDiffCallback : DiffUtil.ItemCallback<ProductWithDetails>() {
        override fun areItemsTheSame(
            oldItem: ProductWithDetails,
            newItem: ProductWithDetails
        ): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(
            oldItem: ProductWithDetails,
            newItem: ProductWithDetails
        ): Boolean {
            return oldItem.product.productId == newItem.product.productId
        }


    }
}