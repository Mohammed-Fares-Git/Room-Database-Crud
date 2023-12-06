package com.MohammedFares.ecomerce_project.presentation.client

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
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.MohammedFares.ecomerce_project.comon.Constantes
import com.MohammedFares.ecomerce_project.comon.Resource
import com.MohammedFares.ecomerce_project.databinding.ClientHomePageBinding
import com.MohammedFares.ecomerce_project.presentation.ProductActivity
import com.MohammedFares.ecomerce_project.presentation.adapters.ProductsAdapter
import com.MohammedFares.ecomerce_project.presentation.adapters.TypesAdapter
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

        binding.products.setHasFixedSize(true)
        //binding.products.isNestedScrollingEnabled = false
        binding.products.layoutManager = GridLayoutManager(requireContext(),2)

        binding.filter.setHasFixedSize(true)
        //binding.products.isNestedScrollingEnabled = false
        binding.filter.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)




        viewLifecycleOwner.lifecycleScope.launch {

            launch {
                productsViewModel.typesStateFlow.collect {types->
                    when (types) {
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
                            val adapter = types.data?.let { TypesAdapter(it) }
                            binding.filter.adapter = adapter
                            Log.d("ahahhhhhh", types.data.toString())
                        }
                    }
                }
            }




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
                        val adapter = ProductsAdapter {
                            requireActivity().startActivity(Intent(requireContext(),ProductActivity::class.java).apply {
                                this.putExtra(Constantes.PRODUCT_ID_KEY,it)
                            })
                        }
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