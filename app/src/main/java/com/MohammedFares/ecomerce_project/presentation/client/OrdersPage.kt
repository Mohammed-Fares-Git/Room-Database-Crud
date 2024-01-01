package com.MohammedFares.ecomerce_project.presentation.client

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.MohammedFares.ecomerce_project.R
import com.MohammedFares.ecomerce_project.databinding.OrdersPageBinding
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


class OrdersPage : Fragment() {

    private lateinit var binding: OrdersPageBinding
    val ordersViewModel: OrdersPageViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = OrdersPageBinding.inflate(inflater, container, false)



        viewLifecycleOwner.lifecycleScope.launch {
            ordersViewModel.ordersStateFlow.collect {
                if (it.size > 0){

                }
            }
        }



        return binding.root
    }

}