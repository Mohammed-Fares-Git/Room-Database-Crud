package com.MohammedFares.ecomerce_project.presentation.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.MohammedFares.ecomerce_project.R
import com.MohammedFares.ecomerce_project.comon.GenderOption
import com.MohammedFares.ecomerce_project.databinding.FilterItemBinding
import com.MohammedFares.ecomerce_project.domain.model.SelectebleColor

class FilterGenderAdapter(val action:(gender: String)->Unit = {}, val ctx: Context) : RecyclerView.Adapter<FilterGenderAdapter.FilterViewHolder>() {

    private lateinit var filterItems: List<GenderOption>

    class FilterViewHolder(val binding: FilterItemBinding): ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilterViewHolder {
        val bind = FilterItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return FilterViewHolder(bind)
    }

    override fun getItemCount(): Int = filterItems.size

    override fun onBindViewHolder(holder: FilterViewHolder, position: Int) {
        val filter = filterItems.get(position)

        holder.binding.fiterName.text = filter.getLocalizedLabel(ctx)

        holder.binding.root.setOnClickListener {
            action(filter.gender)
        }

        if (filter.isSelected) {
            holder.binding.root.setCardBackgroundColor(
                ctx.getColor(R.color.black)
            )
        }

    }


    fun setFilters(filers: List<GenderOption>) {
        this.filterItems = filers
        notifyDataSetChanged()
    }

}