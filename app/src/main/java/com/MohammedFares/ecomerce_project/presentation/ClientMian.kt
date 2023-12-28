package com.MohammedFares.ecomerce_project.presentation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.MohammedFares.ecomerce_project.R
import com.MohammedFares.ecomerce_project.auth.AuthManager
import com.MohammedFares.ecomerce_project.databinding.ActivityClientMianBinding
import com.google.android.material.badge.BadgeDrawable
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ClientMian : AppCompatActivity() {
    private lateinit var authManager: AuthManager
    private lateinit var binding: ActivityClientMianBinding
    //private lateinit var badgeBinding: UtilBadgeBinding
    private lateinit var cartBadge: BadgeDrawable
    private lateinit var likesBadge: BadgeDrawable
    val clientViewModel: ClientViewModel by viewModels()
    val rootViewModel: ClientRootViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityClientMianBinding.inflate(layoutInflater)
        setContentView(binding.root)






        authManager = AuthManager(this)

        val loginState = authManager.isLoggedIn and authManager.isClientLoggedIn()


        if (!loginState) {
            startActivity(Intent(this@ClientMian,LoginMain::class.java))
            finish()
        } else {
            val navController = Navigation.findNavController(this, R.id.client_main_container);
            NavigationUI.setupWithNavController(binding.bnvMain, navController);

            val cartMenuItem: MenuItem = binding.bnvMain.menu.findItem(R.id.cartPage)
            cartBadge = binding.bnvMain.getOrCreateBadge(cartMenuItem.itemId)

            val favoriteMenuItem: MenuItem = binding.bnvMain.menu.findItem(R.id.favoritesPage)
            likesBadge = binding.bnvMain.getOrCreateBadge(favoriteMenuItem.itemId)


            //badgeBinding = UtilBadgeBinding.inflate(layoutInflater,binding.bnvMain,false)




            lifecycleScope.launch {
                clientViewModel.getCurrentCart(
                    clientId = authManager.id,
                    action = {
                        setCartId(it)
                    }
                ).join()

                rootViewModel.getCartItemsCount(authManager.cartId)
                rootViewModel.getLikesCount(1)
                //rootViewModel.getLikesCount(authManager.id)

                rootViewModel.cartItemsCountStateFlow.collect {
                    if (it > 0) {
                        cartBadge.isVisible = true
                        cartBadge.number = it
                    } else {
                        cartBadge.isVisible = false
                        cartBadge.number = it
                    }
                }
            }

            lifecycleScope.launch {


                rootViewModel.getLikesCount(1)
                //rootViewModel.getLikesCount(authManager.id)

                rootViewModel.likesCountStateFlow.collect {
                    if (it > 0) {
                        likesBadge.isVisible = true
                        likesBadge.number = it
                    } else {
                        likesBadge.isVisible = false
                        likesBadge.number = it
                    }
                }
            }


        }

    }

    private fun setCartId(it: Long) {
        authManager.cartId = it
    }

    private suspend fun retrySetCartId() {

    }
}