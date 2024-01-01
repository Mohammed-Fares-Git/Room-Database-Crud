package com.MohammedFares.ecomerce_project.presentation.client

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.MohammedFares.ecomerce_project.R
import com.MohammedFares.ecomerce_project.databinding.ProfilePageBinding
import com.MohammedFares.ecomerce_project.presentation.ClientRootViewModel
import com.squareup.picasso.Picasso
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


class ProfilePage : Fragment() {


    private lateinit var binding: ProfilePageBinding
    val rootViewModel: ClientRootViewModel by activityViewModels()
    val profileViewModel: ProfilePageViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ProfilePageBinding.inflate(inflater,container, false)


        viewLifecycleOwner.lifecycleScope.launch {
            profileViewModel.clientStateFlow.collect {nullableClient->
                nullableClient?.let {
                    binding.clientName.text = "${ it.firstname.lowercase().capitalize() } ${ it.lastname.lowercase().capitalize() }"
                    try {
                        Picasso.get().load(it.profileImage).resize(250,250).into(binding.clientImage)
                    } catch (e: Exception) {
                        Toast.makeText(requireContext(), getText(R.string.provide_valid_url), Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            rootViewModel.likesCountStateFlow.collect {
                if (it > 0) {
                    binding.likesCount.text = it.toString()
                    binding.likesCount.visibility = View.VISIBLE
                } else {
                    binding.likesCount.visibility = View.INVISIBLE
                }
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            rootViewModel.cartItemsCountStateFlow.collect {
                if (it > 0) {
                    binding.cartItemsCount.text = it.toString()
                    binding.cartItemsCount.visibility = View.VISIBLE
                } else {
                    binding.cartItemsCount.visibility = View.INVISIBLE
                }
            }
        }


        return binding.root
    }

}