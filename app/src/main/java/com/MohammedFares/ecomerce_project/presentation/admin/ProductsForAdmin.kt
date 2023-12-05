package com.MohammedFares.ecomerce_project.presentation.admin

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
import androidx.recyclerview.widget.LinearLayoutManager
import com.MohammedFares.ecomerce_project.R
import com.MohammedFares.ecomerce_project.comon.Resource
import com.MohammedFares.ecomerce_project.data.relations.ProductWithDetails
import com.MohammedFares.ecomerce_project.databinding.ProductsForAdminBinding
import com.MohammedFares.ecomerce_project.domain.model.ProductExpendable
import com.MohammedFares.ecomerce_project.presentation.adapters.AdminProductItemAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
@AndroidEntryPoint
class ProductsForAdmin : Fragment() {
    private lateinit var binding: ProductsForAdminBinding
    val productsForAdminViewModel: ProductsForAdminViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ProductsForAdminBinding.inflate(inflater,container,false)

        val action = ProductsForAdminDirections.actionProductsForAdminToAdminProductControl(1)

        viewLifecycleOwner.lifecycleScope.launch {
            productsForAdminViewModel.prodctsStateFlow.collect {
                when (it) {
                    is Resource.Empty -> Unit
                    is Resource.Error -> {
                        binding.errMsg.text = it.message
                        showError()
                    }
                    is Resource.Loading -> {
                        showProgressBar()
                    }
                    is Resource.Success -> {
                        Log.e("fareskkk", it.data.toString())
                        showList(it.data!!)
                        Toast.makeText(requireContext(), it.data.toString(), Toast.LENGTH_SHORT).show()

                    }
                }
            }
        }


        return binding.root
    }

    private fun showProgressBar() {
        binding.adminProductsList.visibility = View.GONE
        binding.errMsg.visibility = View.GONE
        binding.loading.visibility = View.VISIBLE
    }
    private fun showError() {
        binding.adminProductsList.visibility = View.GONE
        binding.errMsg.visibility = View.VISIBLE
        binding.loading.visibility = View.GONE
    }
    private fun showList(p:List<ProductExpendable>) {
        binding.adminProductsList.setHasFixedSize(true)
        binding.adminProductsList.layoutManager = LinearLayoutManager(this.requireContext())
        binding.adminProductsList.adapter = AdminProductItemAdapter(this.requireContext(),p) {
            val action = ProductsForAdminDirections.actionProductsForAdminToAdminProductControl(it)
            Navigation.findNavController(binding.root).navigate(action)
        }
        binding.adminProductsList.visibility = View.VISIBLE
        binding.errMsg.visibility = View.GONE
        binding.loading.visibility = View.GONE
    }


}