package com.MohammedFares.ecomerce_project.presentation.adapters

import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.MohammedFares.ecomerce_project.R
import com.MohammedFares.ecomerce_project.data.entity.CartItem
import com.MohammedFares.ecomerce_project.data.relations.CartItemDetails
import com.MohammedFares.ecomerce_project.databinding.CartItemBinding
import com.MohammedFares.ecomerce_project.databinding.ProductColorItemBinding
import com.MohammedFares.ecomerce_project.domain.model.SelectebleColor
import com.squareup.picasso.Picasso

class CartItemsAdapter(
    val action: (id: Long) -> Unit = {},
    val deleteAction: (cartItem: CartItem)->Unit = {}
) :
    ListAdapter<CartItemDetails, CartItemsAdapter.CartItemViewHolder>(CartItemDiffCallback()) {

    class CartItemViewHolder(val binding: CartItemBinding) : ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartItemViewHolder {
        val bind = CartItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CartItemViewHolder(bind)
    }

    override fun onBindViewHolder(holder: CartItemViewHolder, position: Int) {
        val cartItem = getItem(position).cartItem
        val likes = getItem(position).likes
        val product = getItem(position).product
        val size = getItem(position).size
        val color = getItem(position).color
        val lastIndex = currentList.lastIndex


        if (position >= lastIndex) {
            holder.binding.hrBottom.visibility = View.INVISIBLE
        } else {
            holder.binding.hrBottom.visibility = View.VISIBLE
        }

        holder.binding.productSize.text = "size:${size.sizeName}"

        try {
            Picasso.get().load(product.mainImage).resize(250, 350).into(holder.binding.image)
        } catch (e: Exception) {

        }

        holder.binding.nbr.text = cartItem.quantity.toString()

        holder.binding.productPriceTv.text = "${(product.price * cartItem.quantity).toInt()} MAD"


        holder.binding.root.setOnLongClickListener( object  : View.OnLongClickListener{
            override fun onLongClick(p0: View?): Boolean {
                deleteAction(cartItem)
                return true
            }

        })


    }

    class CartItemDiffCallback : DiffUtil.ItemCallback<CartItemDetails>() {
        override fun areItemsTheSame(oldItem: CartItemDetails, newItem: CartItemDetails): Boolean {
            //Log.e("ahahhhhhh", newItem.toString())
            //Log.e("ahahhhhhh", oldItem.toString())
            return oldItem == newItem
        }

        override fun areContentsTheSame(
            oldItem: CartItemDetails,
            newItem: CartItemDetails
        ): Boolean {
            return oldItem.cartItem.quantity == newItem.cartItem.quantity && oldItem.likes.size == newItem.likes.size
        }

    }

}