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
import kotlinx.coroutines.async
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

        cartPageViewModel.getCartItemsCount(authManager.cartId)

        binding.checkOutBtn.setOnClickListener {

            if (cartId.toInt() != -1) {
                cartPageViewModel.checkOut(order, cart){
                    rootViewModel.getCurrentCart(1) {
                        authManager.cartId
                        rootViewModel.setCartId(authManager.cartId)
                    }
                }
                cartPageViewModel.getCartItemsCount(authManager.cartId)
                cartPageViewModel.getProduct(authManager.cartId)
            } else {
                Toast.makeText(requireContext(), "can't ${cartId}", Toast.LENGTH_SHORT).show()
            }

        }



        if (true) {

            cartId = authManager.cartId
            cartId = 1



            rootViewModel.setCartId(cartId)

            order = Order(clientId = 1, cartId = cartId, orderDate = System.currentTimeMillis() / 1000)


            viewLifecycleOwner.lifecycleScope.launch(Dispatchers.IO) {
                rootViewModel.getCurrentCart(1) {
                    authManager.cartId = it
                    cartId = authManager.cartId
                    rootViewModel.setCartId(it)
                }.join()
                if (cartPageViewModel.getCart(authManager.cartId).size > 0 && authManager.cartId.toInt() != -1) {
                    cart = cartPageViewModel.getCart(authManager.cartId).first()
                }
            }


            viewLifecycleOwner.lifecycleScope.launch {
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

                    /*
                    if (cartId != id && id.toInt() != -1) {
                        rootViewModel.getCartItemsCount(cartId)
                    }

                    if (id.toInt() != -1) {
                        cartId = id
                        authManager.cartId = id

                        val carts = async(Dispatchers.IO){ cartPageViewModel.getCart(cartId) }.await()

                        rootViewModel.getCurrentCart(id) {
                            if (carts.size > 0 && cartId.toInt() != -1) {
                                cart = carts.first()
                            }

                        }
                    }

                     */




                }
            }




            cartId?.let {
                cartPageViewModel.getProduct(it)
            }

            cartPageViewModel.getProduct(authManager.cartId)

        }





        return binding.root
    }


}