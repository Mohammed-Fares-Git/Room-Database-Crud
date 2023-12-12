package com.MohammedFares.ecomerce_project.presentation.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.MohammedFares.ecomerce_project.R
import com.MohammedFares.ecomerce_project.data.entity.Product
import com.MohammedFares.ecomerce_project.data.entity.ProductSubImage
import com.MohammedFares.ecomerce_project.data.relations.ProductWithDetails
import com.MohammedFares.ecomerce_project.databinding.ClientProductItemBinding
import com.squareup.picasso.Picasso

class ProductsAdapter (val ctx: Context, var clientId: Long = 1, val action: (id: Long)->Unit = {}): ListAdapter<ProductWithDetails,ProductsAdapter.ProductsViewHolder>(ProductDiffCallback()) {
    inner class ProductsViewHolder(val binding: ClientProductItemBinding): ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductsViewHolder {
        val bind = ClientProductItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ProductsViewHolder(bind)
    }

    override fun onBindViewHolder(holder: ProductsViewHolder, position: Int) {
        val product = getItem(position)
        Picasso.get().load(product.product.mainImage).into(holder.binding.productImage)
        holder.binding.productName.text = product.product.productName
        holder.binding.productPrice.text = "${ product.product.price } ${ctx.getText(R.string.moroccan_dirham_acronym)}"
        holder.binding.root.setOnClickListener {
            action(product.product.productId)
        }

        if (product.product.sold > 0) {
            holder.binding.productSold.text = "${ product.product.sold } %"
            holder.binding.productSold.visibility = View.VISIBLE
        }

        if (product.product.livreson) {
            holder.binding.productDelevry.visibility = View.VISIBLE
        }
        val likes = product.likes.filter {
            it.clientId == clientId
        }

        if (likes.size > 0) {
            holder.binding.isLiked.setImageResource(R.drawable.heart_solid_24)
        } else {
            holder.binding.isLiked.setImageResource(R.drawable.favorite_ic)
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