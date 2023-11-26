package com.MohammedFares.ecomerce_project.presentation.admin

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.MohammedFares.ecomerce_project.R
import com.MohammedFares.ecomerce_project.databinding.AdminDashBoardBinding


class AdminDashBoard : Fragment() {

    private lateinit var binding: AdminDashBoardBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = AdminDashBoardBinding.inflate(inflater,container,false)

        binding.adminDashProduts.setOnClickListener {

        }

        return binding.root
    }


}