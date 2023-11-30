package com.MohammedFares.ecomerce_project.presentation.admin

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.MohammedFares.ecomerce_project.R
import com.MohammedFares.ecomerce_project.auth.AuthManager
import com.MohammedFares.ecomerce_project.databinding.AdminDashBoardBinding


class AdminDashBoard : Fragment() {

    private lateinit var binding: AdminDashBoardBinding
    private lateinit var authManager: AuthManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = AdminDashBoardBinding.inflate(inflater,container,false)
        authManager = AuthManager(requireContext())

        authManager.username?.let {
            binding.administratorNameTv.text = it
        }


        binding.adminDashProduts.setOnClickListener {
            Navigation.findNavController(binding.root).navigate(R.id.action_adminDashBoard_to_productsForAdmin)
        }

        return binding.root
    }


}