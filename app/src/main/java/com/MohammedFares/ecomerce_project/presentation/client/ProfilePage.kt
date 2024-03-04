package com.MohammedFares.ecomerce_project.presentation.client

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import com.MohammedFares.ecomerce_project.R
import com.MohammedFares.ecomerce_project.auth.AuthManager
import com.MohammedFares.ecomerce_project.comon.Constants
import com.MohammedFares.ecomerce_project.data.entity.Client
import com.MohammedFares.ecomerce_project.databinding.DialogEditClientDetailsBinding
import com.MohammedFares.ecomerce_project.databinding.ProfilePageBinding
import com.MohammedFares.ecomerce_project.presentation.ClientRootViewModel
import com.MohammedFares.ecomerce_project.presentation.LoginMain
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProfilePage : Fragment() {

    private lateinit var binding: ProfilePageBinding
    private lateinit var auth: AuthManager
    private lateinit var dialodbinding: DialogEditClientDetailsBinding
    val rootViewModel: ClientRootViewModel by activityViewModels()
    val profileViewModel: ProfilePageViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ProfilePageBinding.inflate(inflater,container, false)


        auth = AuthManager(requireContext())

        binding.clientName.text = auth.username ?: "client name"
        binding.myOrders.setOnClickListener {
            Navigation.findNavController(binding.root).navigate(R.id.action_profilePage_to_ordersPage)
        }

        binding.myFavorites.setOnClickListener {
            Navigation.findNavController(binding.root).navigate(R.id.action_profilePage_to_favoritesPage)
        }

        binding.myCart.setOnClickListener {
            Navigation.findNavController(binding.root).navigate(R.id.action_profilePage_to_cartPage)
        }

        binding.logOutBtn.setOnClickListener {
            auth.logout()
            requireActivity().startActivity(Intent(requireActivity(),LoginMain::class.java))
            requireActivity().finish()
        }

        viewLifecycleOwner.lifecycleScope.launch {
            profileViewModel.clientStateFlow.collect {nullableClient->
                nullableClient?.let {
                    binding.clientName.text = "${ it.firstname.lowercase().capitalize() } ${ it.lastname.lowercase().capitalize() }"
                    try {
                        Picasso.get().load(it.profileImage).resize(250,250).into(binding.clientImage)
                    } catch (e: Exception) {
                        Toast.makeText(requireContext(), getText(R.string.provide_valid_url), Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            rootViewModel.likesCountStateFlow.collect {
                if (it > 0) {
                    binding.likesCount.text = it.toString()
                    binding.likesCount.visibility = View.VISIBLE
                } else {
                    binding.likesCount.visibility = View.INVISIBLE
                }
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            rootViewModel.cartItemsCountStateFlow.collect {
                if (it > 0) {
                    binding.cartItemsCount.text = it.toString()
                    binding.cartItemsCount.visibility = View.VISIBLE
                } else {
                    binding.cartItemsCount.visibility = View.INVISIBLE
                }
            }
        }


        return binding.root
    }


    fun showProductDetailsDialog(
        context: Context,
        client: Client,
        editAction: (client: Client) -> Unit
    ) {



        val inflater = LayoutInflater.from(context)
        dialodbinding = DialogEditClientDetailsBinding.inflate(inflater)

        val builder = AlertDialog.Builder(context)
        val view = dialodbinding.root


        builder.setView(view)

        val dialog = builder.create()

        client.also {


            Picasso.get().load(it.profileImage).into(dialodbinding.dialogImage)
            dialodbinding.dialogEtClientName.text = Editable.Factory.getInstance().newEditable(it.firstname)
            dialodbinding.dialogEtClientLastName.text = Editable.Factory.getInstance().newEditable(it.lastname)
            dialodbinding.dialogEtClientEmail.text = Editable.Factory.getInstance().newEditable(it.email)
            dialodbinding.dialogEtClienProfileImage.text = Editable.Factory.getInstance().newEditable(it.profileImage)
            dialodbinding.dialogEtClientPassword.text = Editable.Factory.getInstance().newEditable(it.password)
            if (it.gender == Constants.MALE_KEY) {
                dialodbinding.clientRbSexM.isChecked = true
            } else {
                dialodbinding.clientRbSexF.isChecked = true
            }


            dialodbinding.dialogEditBtn.root.setOnClickListener {

                editAction(client)

                dialog.dismiss()
            }

        }

        dialodbinding.dialogEtClienProfileImage.addTextChangedListener(object :
            TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                p0?.let {
                    if (it.length >= 0) {
                        try {
                            Picasso.get().load(it.toString()).placeholder(R.drawable.upload_ic)
                                .into(dialodbinding.dialogImage)
                        } catch (e: Exception) {
                            Toast.makeText(
                                requireContext(),
                                requireActivity().getText(R.string.provide_valid_url),
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }

            }

            override fun afterTextChanged(p0: Editable?) {
            }

        })

        dialog.window?.attributes?.windowAnimations = R.style.DialogAnimation
        dialog.window?.setBackgroundDrawableResource(R.drawable.auth_forms_bg)


        dialog.show()
    }

}