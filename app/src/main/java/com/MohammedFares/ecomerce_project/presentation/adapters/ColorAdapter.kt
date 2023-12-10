package com.MohammedFares.ecomerce_project.presentation.adapters

import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.MohammedFares.ecomerce_project.R
import com.MohammedFares.ecomerce_project.databinding.ProductColorItemBinding
import com.MohammedFares.ecomerce_project.domain.model.SelectebleColor

class ColorAdapter( val action:(id: Long)->Unit) : ListAdapter<SelectebleColor, ColorAdapter.ColorViewHolder>(ClientColorDiffCallback()) {

    class ColorViewHolder(val binding: ProductColorItemBinding): ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ColorViewHolder {
        val bind = ProductColorItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ColorViewHolder(bind)
    }

    override fun onBindViewHolder(holder: ColorViewHolder, position: Int) {
        val color = getItem(position)

        try {
            holder.binding.colorHexCode.setBackgroundColor(Color.parseColor(color.color.colorHexCode))
        } catch (e: Exception) {
            holder.binding.colorHexCode.setBackgroundColor(Color.parseColor("#FFFFFFFF"))
        }

        if (color.isSelected) {
            holder.binding.colorBorder.setBackgroundResource(R.drawable.selected_type)
        }

        holder.binding.root.setOnClickListener {
            action(color.color.colorId)
        }
    }
    class ClientColorDiffCallback : DiffUtil.ItemCallback<SelectebleColor>() {
        override fun areItemsTheSame(oldItem: SelectebleColor, newItem: SelectebleColor): Boolean {
            //Log.e("ahahhhhhh", newItem.toString())
            //Log.e("ahahhhhhh", oldItem.toString())
            return oldItem == newItem
        }

        override fun areContentsTheSame(
            oldItem: SelectebleColor,
            newItem: SelectebleColor
        ): Boolean {
            return oldItem.isSelected == newItem.isSelected
        }

    }

}