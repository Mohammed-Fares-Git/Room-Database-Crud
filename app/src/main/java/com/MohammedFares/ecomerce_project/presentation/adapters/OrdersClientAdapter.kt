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
import com.MohammedFares.ecomerce_project.data.entity.Order
import com.MohammedFares.ecomerce_project.databinding.OrderItemLayoutBinding
import com.MohammedFares.ecomerce_project.databinding.ProductColorItemBinding
import com.MohammedFares.ecomerce_project.domain.model.SelectebleColor
import java.text.SimpleDateFormat
import java.util.Date

class OrdersClientAdapter(val ctx: Context,private var orders: List<Order>) : RecyclerView.Adapter<OrdersClientAdapter.OrderViewHolder>() {



    class OrderViewHolder(val binding: OrderItemLayoutBinding): ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        val bind = OrderItemLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return OrderViewHolder(bind)
    }

    override fun getItemCount(): Int = orders.size

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        val order = orders.get(position)
        val sdf = SimpleDateFormat("EEEEE dd MM yyyy")
        val date = Date(order.orderDate)


        holder.binding.orderDate.text = sdf.format(date)
        holder.binding.orderPrice.text = "Prix Total : ${ order.totalPrice } ${ctx.getText(R.string.moroccan_dirham_acronym)}"
        holder.binding.orderState.text = order.state

    }




}