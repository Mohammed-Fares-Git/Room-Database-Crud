package com.MohammedFares.ecomerce_project.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.MohammedFares.ecomerce_project.data.entity.ProductType
import com.MohammedFares.ecomerce_project.databinding.ProductTypeItemBinding
import com.MohammedFares.ecomerce_project.domain.model.ProductExpendable
import com.squareup.picasso.Picasso

class TypesAdapter(val products: List<ProductType>, val onClickType: (id: Long) -> Unit = {}):
    RecyclerView.Adapter<TypesAdapter.TypesViewHolder>() {
    inner class TypesViewHolder (val binding: com.MohammedFares.ecomerce_project.databinding.ProductTypeItemBinding): ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TypesViewHolder {
        val binding = ProductTypeItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return TypesViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return products.size
    }

    override fun onBindViewHolder(holder: TypesViewHolder, position: Int) {
        val type = products.get(position)

        Picasso.get().load(type.typeImage).into(holder.binding.typeImage)

        holder.binding.typeName.text = type.typeName
    }


}