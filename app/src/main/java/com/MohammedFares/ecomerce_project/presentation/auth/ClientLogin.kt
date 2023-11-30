package com.MohammedFares.ecomerce_project.presentation.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.MohammedFares.ecomerce_project.R
import com.MohammedFares.ecomerce_project.databinding.ClientLoginBinding


class ClientLogin : Fragment() {

    private lateinit var binding: ClientLoginBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ClientLoginBinding.inflate(inflater,container,false)
        binding.goToAdministrationBtn.setOnClickListener {
            Navigation.findNavController(binding.root).navigate(R.id.action_client_login_to_admin_login)
        }
        binding.loginSignupBtn.setOnClickListener {
            Navigation.findNavController(binding.root).navigate(R.id.action_client_login_to_client_signin)
        }
        return binding.root
    }


}