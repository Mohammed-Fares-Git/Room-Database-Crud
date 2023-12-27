package com.MohammedFares.ecomerce_project.presentation.client

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.MohammedFares.ecomerce_project.comon.ClothingType
import com.MohammedFares.ecomerce_project.comon.Constantes
import com.MohammedFares.ecomerce_project.comon.GenderOption
import com.MohammedFares.ecomerce_project.comon.Resource
import com.MohammedFares.ecomerce_project.databinding.ClientHomePageBinding
import com.MohammedFares.ecomerce_project.presentation.ProductActivity
import com.MohammedFares.ecomerce_project.presentation.adapters.FilterGenderAdapter
import com.MohammedFares.ecomerce_project.presentation.adapters.FilterTypeAdapter
import com.MohammedFares.ecomerce_project.presentation.adapters.ProductsAdapter
import com.MohammedFares.ecomerce_project.presentation.adapters.TypesAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class home_page : Fragment() {

    private lateinit var binding: ClientHomePageBinding
    val productsViewModel: HomePageViewModel by viewModels()


    override fun onResume() {
        super.onResume()
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {

        val genderFilters: List<GenderOption> = listOf(GenderOption.Male, GenderOption.Female)
        val typeFilters: List<ClothingType> = listOf(ClothingType.TShirt,ClothingType.Dress,ClothingType.Hoodie,ClothingType.TShirt,ClothingType.Dress)
        // Inflate the layout for this fragment
        binding = ClientHomePageBinding.inflate(inflater, container, false)

        binding.products.setHasFixedSize(true)
        //binding.products.isNestedScrollingEnabled = false
        binding.products.layoutManager = GridLayoutManager(requireContext(), 2)

        binding.filterRv.setHasFixedSize(true)
        //binding.products.isNestedScrollingEnabled = false
        binding.filterRv.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)


        val filterGenderAdapter =
            FilterGenderAdapter(ctx = requireActivity(), action = { selectedGender ->
                productsViewModel.setProductsLitState(selectedGender = selectedGender)
            })
        filterGenderAdapter.setFilters(genderFilters)
        binding.filter.genderRv.setHasFixedSize(true)
        binding.filter.genderRv.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.filter.genderRv.adapter = filterGenderAdapter

        val filterTypeAdapter =
            FilterTypeAdapter(ctx = requireActivity(), action = { selectedType ->
                Toast.makeText(requireContext(), selectedType, Toast.LENGTH_SHORT).show()
                productsViewModel.setProductsLitState(
                    selectedType = selectedType
                )
            })
        filterTypeAdapter.setFilters(typeFilters)
        binding.filter.typeRv.setHasFixedSize(true)
        binding.filter.typeRv.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.filter.typeRv.adapter = filterTypeAdapter


        productsViewModel.getProducts()


        viewLifecycleOwner.lifecycleScope.launch {

            launch {
                productsViewModel.typesStateFlow.collect { types ->
                    when (types) {
                        is Resource.Empty -> {}

                        is Resource.Error -> {}

                        is Resource.Loading -> {}

                        is Resource.Success -> {
                            val adapter = types.data?.let { TypesAdapter(it) }
                            binding.filterRv.adapter = adapter
                            Log.d("ahahhhhhh", types.data.toString())
                        }
                    }
                }
            }

            launch {
                productsViewModel.productsListScreenState.collect {
                    productsViewModel.getFilteredProducts(it)
                }
            }



            productsViewModel.productStateFlow.collect {
                when (it) {
                    is Resource.Empty -> {}

                    is Resource.Error -> {
                        Toast.makeText(requireContext(), it.message, Toast.LENGTH_LONG).show()
                    }

                    is Resource.Loading -> {}

                    is Resource.Success -> {

                        if (it.data!!.size > 0 && !it.data.isEmpty()) {
                            val adapter = ProductsAdapter(ctx = requireContext(), action =  {
                                requireActivity().startActivity(Intent(
                                    requireContext(), ProductActivity::class.java
                                ).apply {
                                    this.putExtra(Constantes.PRODUCT_ID_KEY, it)
                                })
                            }, putLike = { productId, clientId ->
                                productsViewModel.putLike(productId,clientId)
                            }, removeLike = { productLike ->
                                productsViewModel.removeLike(productLike)
                            })
                            binding.products.adapter = adapter
                            adapter.submitList(it.data)
                            Log.d("ahahhhhhh", it.data.toString())
                        }
                    }
                }
            }
        }

        binding.filter.closeBtn.setOnClickListener {
            productsViewModel.getProducts()
            hideFilter()
        }
        binding.searchTogel.setOnClickListener {
            showFilter()
        }



        binding.filter.filterFreeDelevry.setOnCheckedChangeListener(object : CompoundButton.OnCheckedChangeListener{
            override fun onCheckedChanged(p0: CompoundButton?, p1: Boolean) {
                if (p1) {
                    productsViewModel.setProductsLitState(freeDelevry = true)
                } else {
                    productsViewModel.setProductsLitState(freeDelevry = null)
                }
            }

        })


        binding.filter.filterPromo.setOnClickListener {
            if (binding.filter.filterFreeDelevry.isChecked) {
                productsViewModel.setProductsLitState(
                    productsViewModel.productsListScreenState.value.copy(
                        promo = true
                    )
                )
            } else {
                productsViewModel.setProductsLitState(
                    productsViewModel.productsListScreenState.value.copy(
                        promo = null
                    )
                )
            }
        }

        binding.filter.filterFavorite.setOnClickListener {
            if (binding.filter.filterFavorite.isChecked) {
                productsViewModel.setProductsLitState(
                    productsViewModel.productsListScreenState.value.copy(
                        promo = true
                    )
                )
            } else {
                productsViewModel.setProductsLitState(
                    productsViewModel.productsListScreenState.value.copy(
                        promo = null
                    )
                )
            }
        }


        return binding.root
    }


    fun showFilter() {
        binding.filter.root.visibility = View.VISIBLE
        binding.poter.visibility = View.GONE
        binding.filterRv.visibility = View.GONE
    }


    fun hideFilter() {
        binding.filter.root.visibility = View.GONE
        binding.poter.visibility = View.VISIBLE
        binding.filterRv.visibility = View.VISIBLE
    }


}