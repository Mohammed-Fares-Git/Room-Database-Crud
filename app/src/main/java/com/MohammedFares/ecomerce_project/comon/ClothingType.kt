package com.MohammedFares.ecomerce_project.comon

import android.content.Context
import com.MohammedFares.ecomerce_project.R

sealed class ClothingType (val type: String, var isSelected: Boolean = false) {
    abstract fun getLocalizedLabel(context: Context): String

    object TShirt : ClothingType(Constantes.T_SHIRT) {
        override fun getLocalizedLabel(context: Context): String = context.getString(R.string.t_shirt)
    }
    object Dress : ClothingType(Constantes.DRESS) {
        override fun getLocalizedLabel(context: Context): String = context.getString(R.string.dress)
    }

    object Hoodie : ClothingType(Constantes.HOODIE) {
        override fun getLocalizedLabel(context: Context): String = context.getString(R.string.hoodie)
    }
}
