package com.MohammedFares.ecomerce_project.presentation.client

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.MohammedFares.ecomerce_project.R
import com.MohammedFares.ecomerce_project.comon.Resource
import com.MohammedFares.ecomerce_project.databinding.ClientHomePageBinding
import com.MohammedFares.ecomerce_project.presentation.adapters.ProductsAdapter
import com.MohammedFares.ecomerce_project.presentation.admin.ProductsForAdminViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class home_page : Fragment() {

    private lateinit var binding: ClientHomePageBinding
    val productsViewModel: HomePageViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = ClientHomePageBinding.inflate(inflater,container,false)


        viewLifecycleOwner.lifecycleScope.launch {
            productsViewModel.productStateFlow.collect {
                when (it) {
                    is Resource.Empty -> {
                        Toast.makeText(requireContext(), "empty", Toast.LENGTH_SHORT).show()
                    }
                    is Resource.Error -> {
                        Toast.makeText(requireContext(), "error", Toast.LENGTH_SHORT).show()
                    }
                    is Resource.Loading -> {
                        Toast.makeText(requireContext(), "loading", Toast.LENGTH_SHORT).show()
                    }
                    is Resource.Success -> {
                        Toast.makeText(requireContext(), "success", Toast.LENGTH_SHORT).show()
                        val adapter = ProductsAdapter()
                        binding.products.layoutManager = GridLayoutManager(requireContext(),2)
                        binding.products.adapter = adapter
                        adapter.submitList(it.data)

                        Log.d("ahahhhhhh", it.data.toString())
                    }
                }
            }
        }


        return binding.root
    }

}