package com.MohammedFares.ecomerce_project.presentation.admin

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.MohammedFares.ecomerce_project.R
import com.MohammedFares.ecomerce_project.databinding.ProductsForAdminBinding

class ProductsForAdmin : Fragment() {
    private lateinit var binding: ProductsForAdminBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ProductsForAdminBinding.inflate(inflater,container,false)
        return binding.root
    }

}