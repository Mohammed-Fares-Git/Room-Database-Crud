package com.MohammedFares.ecomerce_project.presentation.adapters

import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.MohammedFares.ecomerce_project.R
import com.MohammedFares.ecomerce_project.databinding.ProductColorItemBinding
import com.MohammedFares.ecomerce_project.domain.model.SelectebleColor

class ColorAdapter2(val action:(id: Long)->Unit) : RecyclerView.Adapter<ColorAdapter2.ColorViewHolder>() {

    private lateinit var colors: List<SelectebleColor>

    class ColorViewHolder(val binding: ProductColorItemBinding): ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ColorViewHolder {
        val bind = ProductColorItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ColorViewHolder(bind)
    }

    override fun getItemCount(): Int = colors.size

    override fun onBindViewHolder(holder: ColorViewHolder, position: Int) {
        val color = colors.get(position)

        try {
            holder.binding.colorHexCode.setBackgroundColor(Color.parseColor(color.color.colorHexCode))
        } catch (e: Exception) {
            holder.binding.colorHexCode.setBackgroundColor(Color.parseColor("#FFFFFFFF"))
        }

        if (color.isSelected) {
            holder.binding.colorBorder.setBackgroundResource(R.drawable.selected_type)
        } else {
            holder.binding.colorBorder.setBackgroundResource(R.drawable.un_selected_type)
        }

        holder.binding.root.setOnClickListener {
            action(color.color.colorId)
        }
    }


    fun setColors(colors: List<SelectebleColor>) {
        this.colors = colors
        notifyDataSetChanged()
    }

}