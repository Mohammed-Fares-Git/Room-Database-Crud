package com.MohammedFares.ecomerce_project.presentation.admin

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.MohammedFares.ecomerce_project.R
import com.MohammedFares.ecomerce_project.comon.Constants
import com.MohammedFares.ecomerce_project.comon.Resource
import com.MohammedFares.ecomerce_project.comon.Utils
import com.MohammedFares.ecomerce_project.data.entity.Product
import com.MohammedFares.ecomerce_project.data.entity.ProductBrand
import com.MohammedFares.ecomerce_project.data.entity.ProductType
import com.MohammedFares.ecomerce_project.data.relations.ProductWithDetails
import com.MohammedFares.ecomerce_project.databinding.DialogProductMainDetailsBinding
import com.MohammedFares.ecomerce_project.databinding.DialogTypeBinding
import com.MohammedFares.ecomerce_project.databinding.ProductsForAdminBinding
import com.MohammedFares.ecomerce_project.domain.model.ProductExpendable
import com.MohammedFares.ecomerce_project.presentation.adapters.AdminProductItemAdapter
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
@AndroidEntryPoint
class ProductsForAdmin : Fragment() {
    private lateinit var binding: ProductsForAdminBinding
    private lateinit var dialogProduct: DialogProductMainDetailsBinding
    val productsForAdminViewModel: ProductsForAdminViewModel by viewModels()
    val dialogViewModel: AdminProductControlDialogViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ProductsForAdminBinding.inflate(inflater,container,false)

        val action = ProductsForAdminDirections.actionProductsForAdminToAdminProductControl(1)

        binding.addProduct.setOnClickListener {
            addProductDialog(
                requireContext(),

            )
        }
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

    fun addProductDialog(
        context: Context,
        add: (product: Product) -> Unit = {}
    ) {

        var types = mutableListOf<ProductType>()
        var brands = mutableListOf<ProductBrand>()

        var isImagevalid = true

        val inflater = LayoutInflater.from(context)
        dialogProduct = DialogProductMainDetailsBinding.inflate(inflater)

        val builder = AlertDialog.Builder(context)
        val view = dialogProduct.root


        builder.setView(view)

        val dialog = builder.create()


        dialogProduct.dialogEditBtn.root.visibility = View.GONE

        //Picasso.get().load(it.mainImage).into(dialogProduct.dialogImage)
        var product = Product(
            productName = dialogProduct.dialogEtProductName.text.toString(),
            productDesc = dialogProduct.dialogEtProductDesc.text.toString(),
            mainImage = dialogProduct.dialogEtProductMainImageUrl.text.toString(),
            gender = if (dialogProduct.productRbSexM.isChecked) Constants.MALE_KEY else Constants.FEMALE_KEY,
            price = 0.0,
            quantity = 0,
            sold = 0,
            livreson = dialogProduct.dialogSwitchDelevry.isChecked
        )



        viewLifecycleOwner.lifecycleScope.launch {

            launch {


                dialogViewModel.brandsStateFlow.collect {
                    when (it) {
                        is Resource.Empty -> {}
                        is Resource.Error -> {}
                        is Resource.Loading -> {}
                        is Resource.Success -> {
                            it.data!!.map {
                                brands.add(it)
                            }
                            val brandsAdapter = object : ArrayAdapter<ProductBrand>(requireContext(), R.layout.type_selection_item, brands) {
                                override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
                                    return createItemView(position, convertView, parent)
                                }

                                override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
                                    return createItemView(position, convertView, parent)
                                }

                                private fun createItemView(position: Int, convertView: View?, parent: ViewGroup): View {
                                    val itemView = convertView ?: LayoutInflater.from(context)
                                        .inflate(R.layout.type_selection_item, parent, false)

                                    try {
                                        Picasso.get().load(brands[position].brandImage).resize(100, 100)
                                            .into(itemView.findViewById<ImageView>(R.id.selection_item_image))
                                    } catch (e: Exception) {
                                        // Handle Picasso exception
                                    }

                                    itemView.findViewById<TextView>(R.id.selection_item_name).text = brands[position].brandName

                                    return itemView
                                }
                            }


                            dialogProduct.selectBrand.root.adapter = brandsAdapter



                        }
                    }
                }
            }


            dialogViewModel.typesStateFlow.collect {
                when (it) {
                    is Resource.Empty -> {}
                    is Resource.Error -> {}
                    is Resource.Loading -> {
                        Toast.makeText(requireContext(), "types", Toast.LENGTH_SHORT).show()
                    }
                    is Resource.Success -> {
                        it.data!!.map {
                            types.add(it)
                        }
                        val typesAdapter = object : ArrayAdapter<ProductType>(requireContext(), R.layout.type_selection_item, types) {
                            override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
                                return createItemView(position, convertView, parent)
                            }

                            override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
                                return createItemView(position, convertView, parent)
                            }

                            private fun createItemView(position: Int, convertView: View?, parent: ViewGroup): View {
                                val itemView = convertView ?: LayoutInflater.from(context)
                                    .inflate(R.layout.type_selection_item, parent, false)

                                try {
                                    Picasso.get().load(types[position].typeImage).resize(100, 100)
                                        .into(itemView.findViewById<ImageView>(R.id.selection_item_image))
                                } catch (e: Exception) {
                                    // Handle Picasso exception
                                }

                                itemView.findViewById<TextView>(R.id.selection_item_name).text = types[position].typeName

                                return itemView
                            }
                        }


                        dialogProduct.selectType.root.adapter = typesAdapter


                    }
                }
            }


        }



        dialogProduct.selectType.root.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                product.typeId = types[p2].typeId
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                product.typeId = 1
            }
        }

        dialogProduct.selectBrand.root.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                product.brandId = brands[p2].brandId
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                product.brandId = 1
            }
        }



        dialogProduct.dialogEditBtn.root.setOnClickListener {

            product = Product(
                productName = dialogProduct.dialogEtProductName.text.toString(),
                productDesc = dialogProduct.dialogEtProductDesc.text.toString(),
                mainImage = dialogProduct.dialogEtProductMainImageUrl.text.toString(),
                gender = if (dialogProduct.productRbSexM.isChecked) Constants.MALE_KEY else Constants.FEMALE_KEY,
                price = dialogProduct.dialogEtProductPrice.text.toString().toDouble(),
                quantity = dialogProduct.dialogEtProductQuantity.text.toString().toInt(),
                sold = dialogProduct.dialogEtProductPromotion.text.toString().toInt(),
                livreson = dialogProduct.dialogSwitchDelevry.isChecked
            )


            Utils.validateProductForm(dialogProduct,product,(product.typeId ?: 1),(product.brandId ?: 1))?.let {
                product = it
            }


            add(product)


            dialog.dismiss()
        }


        dialog.window?.attributes?.windowAnimations = R.style.DialogAnimation
        dialog.window?.setBackgroundDrawableResource(R.drawable.auth_forms_bg)


        dialog.show()
    }


}