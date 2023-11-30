package com.MohammedFares.ecomerce_project.presentation.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.MohammedFares.ecomerce_project.data.relations.ProductWithDetails
import com.MohammedFares.ecomerce_project.databinding.AdminProductItemDetailsBinding
import com.MohammedFares.ecomerce_project.domain.model.ProductExpendable
import com.squareup.picasso.Picasso
import dagger.hilt.android.qualifiers.ApplicationContext

class AdminProductItemAdapter(val ctx: Context, val products: List<ProductExpendable>):
    RecyclerView.Adapter<AdminProductItemAdapter.AdminProductItemViewHolder>() {
    inner class AdminProductItemViewHolder (val binding: AdminProductItemDetailsBinding): ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdminProductItemViewHolder {
        val binding = AdminProductItemDetailsBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return AdminProductItemViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return products.size
    }

    override fun onBindViewHolder(holder: AdminProductItemViewHolder, position: Int) {
        val product = products.get(position)

        if (product.isEpended) {
            holder.binding.productBottomItem.visibility = View.VISIBLE
        } else {
            holder.binding.productBottomItem.visibility = View.GONE
        }
        Picasso.get().load(product.productWithDetails.product.mainImage).resize(200,300).into(holder.binding.productImage)
        holder.binding.prodctName.text = product.productWithDetails.product.productName
        holder.binding.prodctPrice.text = "${ product.productWithDetails.product.price }"
        holder.binding.prodctDateOfCreation.text = "${ product.productWithDetails.product.price }"

        val colorAdapter = AdminColorAdapter()
        val sizeAdapter = AdminSizesAdapter()
        val imagesAdapter = AdminSubImagesAdapter()

        holder.binding.productColores.layoutManager = LinearLayoutManager(ctx,LinearLayoutManager.HORIZONTAL,false)
        holder.binding.productColores.adapter = colorAdapter
        colorAdapter.submitList(product.productWithDetails.colors)

        holder.binding.productSizes.layoutManager = LinearLayoutManager(ctx,LinearLayoutManager.HORIZONTAL,false)
        holder.binding.productSizes.adapter = sizeAdapter
        sizeAdapter.submitList(product.productWithDetails.sizes)

        holder.binding.productImages.layoutManager = LinearLayoutManager(ctx,LinearLayoutManager.HORIZONTAL,false)
        holder.binding.productImages.adapter = imagesAdapter
        imagesAdapter.submitList(product.productWithDetails.subImages)


        product.productWithDetails.brand?.let {
            holder.binding.productBrand.brandName.text = it.brandName
            Picasso.get().load(it.brandImage).resize(50,50).into(holder.binding.productBrand.brandeImage)

        }

        holder.binding.root.setOnClickListener {
            product.isEpended = !product.isEpended
            notifyItemChanged(position)
        }



    }


}