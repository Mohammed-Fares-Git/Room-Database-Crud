package com.MohammedFares.ecomerce_project.presentation.client

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.MohammedFares.ecomerce_project.R
import com.MohammedFares.ecomerce_project.databinding.OrdersPageBinding
import com.MohammedFares.ecomerce_project.presentation.adapters.OrdersClientAdapter
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


class OrdersPage : Fragment() {

    private lateinit var binding: OrdersPageBinding
    private lateinit var adapter: OrdersClientAdapter
    val ordersViewModel: OrdersPageViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = OrdersPageBinding.inflate(inflater, container, false)


        binding.orders.layoutManager = LinearLayoutManager(requireContext())
        binding.orders.setHasFixedSize(true)


        viewLifecycleOwner.lifecycleScope.launch {
            ordersViewModel.ordersStateFlow.collect {
                if (it.size > 0){
                    binding.orders.adapter = OrdersClientAdapter(
                        ctx = requireContext(),
                        orders = it
                    )
                }
            }
        }



        return binding.root
    }

}