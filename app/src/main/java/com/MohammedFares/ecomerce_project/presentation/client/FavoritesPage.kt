package com.MohammedFares.ecomerce_project.presentation.client

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.MohammedFares.ecomerce_project.auth.AuthManager
import com.MohammedFares.ecomerce_project.comon.Constantes
import com.MohammedFares.ecomerce_project.comon.Resource
import com.MohammedFares.ecomerce_project.databinding.FavoritesPageBinding
import com.MohammedFares.ecomerce_project.presentation.ClientRootViewModel
import com.MohammedFares.ecomerce_project.presentation.ProductActivity
import com.MohammedFares.ecomerce_project.presentation.adapters.ProductsAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FavoritesPage : Fragment() {

    private lateinit var binding: FavoritesPageBinding
    private lateinit var authManager: AuthManager
    private lateinit var adapter: ProductsAdapter
    val favoritesPageViewModel: FavoritesPageViewModel by viewModels()
    val rootViewModel: ClientRootViewModel by activityViewModels()
    val homePageViewModel: HomePageViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FavoritesPageBinding.inflate(inflater,container,false)


        adapter = ProductsAdapter(
            ctx = requireContext(),
            clientId = 1,
            action = {
                requireActivity().startActivity(
                    Intent(
                    requireContext(), ProductActivity::class.java
                ).apply {
                    this.putExtra(Constantes.PRODUCT_ID_KEY, it)
                })
            },

            removeLike = {
                homePageViewModel.removeLike(it)
                favoritesPageViewModel.getProducts(1)
            }

        )
        binding.products.setHasFixedSize(true)
        binding.products.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.products.adapter = adapter


        authManager = AuthManager(requireContext())

        if (true) {
            viewLifecycleOwner.lifecycleScope.launch {
                favoritesPageViewModel.favoriteProductStateFlow.collect {
                    when (it) {
                        is Resource.Error -> {
                            binding.errorMessage.text = it.message
                            binding.errorMessage.visibility = View.VISIBLE
                            binding.products.visibility = View.GONE
                        }
                        is Resource.Success -> {
                            binding.errorMessage.visibility = View.GONE
                            binding.products.visibility = View.VISIBLE
                            adapter.submitList(it.data)
                        }
                        else -> {}
                    }
                }
            }
        }





        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        favoritesPageViewModel.getProducts(1)
    }


}