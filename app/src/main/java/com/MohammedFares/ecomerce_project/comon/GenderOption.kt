package com.MohammedFares.ecomerce_project.comon

import android.content.Context
import com.MohammedFares.ecomerce_project.R


sealed class GenderOption (val gender: String, val isSelected: Boolean = false) {
    abstract fun getLocalizedLabel(context: Context): String

    object Male : GenderOption(Constants.MALE_KEY) {
        override fun getLocalizedLabel(context: Context): String = context.getString(R.string.male)
    }
    object Female : GenderOption(Constants.FEMALE_KEY) {
        override fun getLocalizedLabel(context: Context): String = context.getString(R.string.female)
    }
}