package com.MohammedFares.ecomerce_project.presentation.client

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.MohammedFares.ecomerce_project.R
import com.MohammedFares.ecomerce_project.auth.AuthManager
import com.MohammedFares.ecomerce_project.comon.Resource
import com.MohammedFares.ecomerce_project.data.entity.Cart
import com.MohammedFares.ecomerce_project.data.entity.Order
import com.MohammedFares.ecomerce_project.databinding.CartPageBinding
import com.MohammedFares.ecomerce_project.presentation.ClientRootViewModel
import com.MohammedFares.ecomerce_project.presentation.adapters.CartItemsAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CartPage : Fragment() {

    private lateinit var authManager: AuthManager

    private lateinit var binding: CartPageBinding
    private lateinit var adapter: CartItemsAdapter
    private lateinit var order: Order
    private lateinit var cart: Cart
    val cartPageViewModel: CartPageViewModel by viewModels()
    val rootViewModel: ClientRootViewModel by viewModels()
    var cartId: Long = -1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = CartPageBinding.inflate(inflater,container,false)


        authManager = AuthManager(requireContext())

        adapter = CartItemsAdapter(
            deleteAction = {
                cartPageViewModel.removeFromCart(it)
            }
        )
        binding.cartItemsRv.layoutManager = LinearLayoutManager(requireContext())
        binding.cartItemsRv.adapter = adapter

        binding.checkOutBtn.setOnClickListener {

            cartPageViewModel.checkOut(order, cart)
        }



        if (true) {

            cartId = authManager.cartId

            rootViewModel.setCartId(cartId)

            order = Order(clientId = authManager.id, cartId = authManager.cartId, orderDate = System.currentTimeMillis() / 1000)


            viewLifecycleOwner.lifecycleScope.launch(Dispatchers.IO) {
                if (cartPageViewModel.getCart(cartId).size > 0 && cartId.toInt() != -1) {
                    cart = cartPageViewModel.getCart(cartId).first()
                }
            }


            viewLifecycleOwner.lifecycleScope.launch {
                cartPageViewModel.getCartItemsCount(cartId)
                cartPageViewModel.cartItemsCountStateFlow.collect {
                    binding.cartItemsNbr.text = it.toString()
                }
            }

            viewLifecycleOwner.lifecycleScope.launch {

                cartPageViewModel.itemesStateFlow.collect {
                    when (it) {
                        is Resource.Empty -> {}
                        is Resource.Error -> {
                            Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                        }
                        is Resource.Loading -> {}
                        is Resource.Success -> {
                            val cartItems = it.data

                            adapter.submitList(cartItems)
                            var price = 0.0


                            cartItems?.forEach {
                                price += it.cartItem.quantity * it.product.price
                            }
                            order.totalPrice = price

                            binding.totalPrice.text = "${ price } ${R.string.moroccan_dirham_acronym}"
                        }
                    }
                }


            }

            viewLifecycleOwner.lifecycleScope.launch {
                rootViewModel.cartIdStateFlow.collect {id->

                    if (cartId != id) {
                        rootViewModel.getCartItemsCount(cartId)
                    }

                    cartId = id
                    authManager.cartId = id

                    val carts = cartPageViewModel.getCart(cartId)

                    rootViewModel.getCurrentCart(id) {
                        if (carts.size > 0 && cartId.toInt() != -1) {
                            cart = carts.first()
                        }

                    }
                }
            }


            cartId?.let {
                cartPageViewModel.getProduct(it)
            }

        }





        return binding.root
    }


}