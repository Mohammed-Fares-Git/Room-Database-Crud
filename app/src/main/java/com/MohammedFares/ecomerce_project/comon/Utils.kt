package com.MohammedFares.ecomerce_project.comon

import com.MohammedFares.ecomerce_project.data.entity.Product
import com.MohammedFares.ecomerce_project.databinding.DialogProductMainDetailsBinding

object Utils {
    fun validateProductForm(b: DialogProductMainDetailsBinding, p: Product, id0: Long, id1: Long) : Product? {
        if (b.dialogEtProductName.text.isBlank()) {
            return null
        }

        if (b.dialogEtProductPrice.text.isBlank()) {
            return null
        }

        if (b.dialogEtProductPromotion.text.isBlank()) {
            return null
        }

        if (b.dialogEtProductDesc.text.isBlank()) {
            return null
        }

        if (b.dialogEtProductQuantity.text.isBlank()) {
            return null
        }

        if (b.dialogEtProductMainImageUrl.text.isBlank()) {
            return null
        }

        p.mainImage = b.dialogEtProductMainImageUrl.text.toString()
        p.productName = b.dialogEtProductName.text.toString()
        p.productDesc = b.dialogEtProductDesc.text.toString()
        p.quantity =  b.dialogEtProductQuantity.text.toString().toInt()
        p.sold = b.dialogEtProductPromotion.text.toString().toInt()
        p.typeId = id0
        p.brandId = id1

        return p
    }



}