package com.MohammedFares.ecomerce_project.presentation.auth

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.MohammedFares.ecomerce_project.auth.AuthManager
import com.MohammedFares.ecomerce_project.comon.Constants
import com.MohammedFares.ecomerce_project.comon.Resource
import com.MohammedFares.ecomerce_project.databinding.AdminLoginBinding
import com.MohammedFares.ecomerce_project.presentation.AdminMain
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class AdminLogin : Fragment() {

    private lateinit var binding: AdminLoginBinding
    private lateinit var authManager: AuthManager

    val authViewModel: AdminLoginViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = AdminLoginBinding.inflate(inflater,container,false)
        authManager = AuthManager(requireContext())

        viewLifecycleOwner.lifecycleScope.launch {
            authViewModel.authStateFlow.collect {
                when(it){
                    is Resource.Empty -> Unit
                    is Resource.Error -> {
                        Log.d("fares", it.message.toString())
                        Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                        hideProgressBar()
                    }
                    is Resource.Loading -> {
                        showProgressBar()
                    }
                    is Resource.Success -> {
                        hideProgressBar()
                        Toast.makeText(requireContext(),"${it.data?.firstname} ${it.data?.lastname}", Toast.LENGTH_LONG).show()
                        authManager.login("${it.data!!.firstname} ${it.data!!.lastname}",it.data.adminId,it.data!!.email,Constants.ADMIN_KEY)
                        startActivity(Intent(requireActivity(),AdminMain::class.java))
                        requireActivity().finish()
                    }
                }
            }
        }

        binding.loginBtn.setOnClickListener {
            authViewModel.checkAdmin(binding.loginEmail.text.toString(),binding.loginPass.text.toString())
        }

        return binding.root
    }

    private fun showProgressBar() {
        binding.loginBtn.visibility = View.GONE
        binding.loginProgressBar.visibility = View.VISIBLE
    }
    private fun hideProgressBar() {
        binding.loginBtn.visibility = View.VISIBLE
        binding.loginProgressBar.visibility = View.GONE
    }
}