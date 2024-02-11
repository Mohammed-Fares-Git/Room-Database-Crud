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
import androidx.navigation.Navigation
import com.MohammedFares.ecomerce_project.R
import com.MohammedFares.ecomerce_project.auth.AuthManager
import com.MohammedFares.ecomerce_project.comon.Constants
import com.MohammedFares.ecomerce_project.comon.Resource
import com.MohammedFares.ecomerce_project.databinding.ClientLoginBinding
import com.MohammedFares.ecomerce_project.presentation.ClientMian
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ClientLogin : Fragment() {

    private lateinit var binding: ClientLoginBinding
    private lateinit var authManager: AuthManager
    val authViewModel: ClientLoginViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        authManager = AuthManager(this.requireContext())

        binding = ClientLoginBinding.inflate(inflater,container,false)
        binding.goToAdministrationBtn.setOnClickListener {
            Navigation.findNavController(binding.root).navigate(R.id.action_client_login_to_admin_login)
        }
        binding.loginSignupBtn.setOnClickListener {
            Navigation.findNavController(binding.root).navigate(R.id.action_client_login_to_client_signin)
        }


        binding.loginBtn.setOnClickListener {
            authViewModel.checkClient(binding.loginEmail.text.toString(),binding.loginPass.text.toString())
        }



        viewLifecycleOwner.lifecycleScope.launch {
            authViewModel.authStateFlow.collect {
                when(it){
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
                        authManager.login("${it.data!!.firstname} ${it.data!!.lastname}",it.data.clientId,it.data!!.email,
                            Constants.CLIENT_KEY)
                        startActivity(Intent(requireActivity(), ClientMian::class.java))
                        requireActivity().finish()
                    }
                    else -> {}
                }
            }
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