package com.MohammedFares.ecomerce_project.presentation.auth

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
import com.MohammedFares.ecomerce_project.comon.GenderOption
import com.MohammedFares.ecomerce_project.comon.Resource
import com.MohammedFares.ecomerce_project.databinding.ClientSigninBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ClientSignin : Fragment() {

    private lateinit var binding: ClientSigninBinding
    private lateinit var authManager: AuthManager
    val authSignUp: ClientSignUpViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ClientSigninBinding.inflate(inflater, container, false)

        authManager = AuthManager(requireContext())

        viewLifecycleOwner.lifecycleScope.launch {
            authSignUp.authStateFlow.collect {
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
                        Toast.makeText(requireContext(),"Success", Toast.LENGTH_LONG).show()
                        Navigation.findNavController(binding.root).navigate(R.id.action_client_signin_to_client_login)
                    }
                    else -> {}
                }
            }
        }



        binding.loginBtn.setOnClickListener {

            val selectedRB = binding.signupSexe.checkedRadioButtonId
            val selectedGender = if (selectedRB == binding.signupRbSexF.id) Constants.FEMALE_KEY else Constants.MALE_KEY


            authSignUp.checkClient(
                binding.signupFirstName.text.toString(),
                binding.signupLastName.text.toString(),
                binding.signupEmail.text.toString(),
                binding.signupPass.text.toString(),
                selectedGender
            )

            Toast.makeText(requireContext(), binding.signupFirstName.text, Toast.LENGTH_SHORT).show()
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