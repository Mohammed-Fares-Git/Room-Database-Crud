package com.MohammedFares.ecomerce_project.presentation.admin

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.MohammedFares.ecomerce_project.R
import com.MohammedFares.ecomerce_project.comon.Resource
import com.MohammedFares.ecomerce_project.data.entity.ProductSubImage
import com.MohammedFares.ecomerce_project.databinding.AdminProductControlBinding
import com.MohammedFares.ecomerce_project.databinding.DialogImagesBinding
import com.MohammedFares.ecomerce_project.presentation.adapters.AdminColorAdapter
import com.MohammedFares.ecomerce_project.presentation.adapters.AdminSizesAdapter
import com.MohammedFares.ecomerce_project.presentation.adapters.AdminSubImagesAdapter
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AdminProductControl : Fragment() {


    private lateinit var binding: AdminProductControlBinding
    private lateinit var dialogImage: DialogImagesBinding

    //val adminProductControleViewModel: AdminProductControleViewModel by viewModels()
    val adminProductControleViewModel: AdminProductControleViewModel by hiltNavGraphViewModels(R.id.administration_graph_xml)

    val args: AdminProductControlArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = AdminProductControlBinding.inflate(inflater,container,false)

        val id = args.productId ?: 1


        adminProductControleViewModel.getProductById(id)
        viewLifecycleOwner.lifecycleScope.launch {
            adminProductControleViewModel.productStateFlow.collect{
                when (it) {
                    is Resource.Empty -> Unit
                    is Resource.Error -> Unit
                    is Resource.Loading -> Unit
                    is Resource.Success -> {
                        val product = it.data

                        product?.let {
                            Picasso.get().load(it.product.mainImage).placeholder(R.drawable.no_connectio_50).into(binding.adminControleProductMainImage)
                        }

                        binding.adminControleProductName.text = product?.product?.productName

                        val imageAdapter = AdminSubImagesAdapter({
                            showImageDialog(context = requireContext(), productSubImage = it, editAction = {
                                adminProductControleViewModel.editPeodactImage(it)
                            } )
                        }, {
                            adminProductControleViewModel.deletePeodactImage(it)
                        })

                        binding.adminControleEditProductImages.root.setOnClickListener {
                            showImageDialog(context = requireContext(), prdtId = product!!.product.productId, addAction = {
                                Toast.makeText(requireContext(), "add", Toast.LENGTH_SHORT).show()
                            })
                        }

                        binding.adminControleProductRvImages.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)
                        binding.adminControleProductRvImages.adapter = imageAdapter
                        imageAdapter.submitList(product?.subImages)

                        val colorAdapter = AdminColorAdapter()
                        binding.adminControleProductRvColors.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)
                        binding.adminControleProductRvColors.adapter = colorAdapter
                        colorAdapter.submitList(product?.colors)

                        val sizeAdapter = AdminSizesAdapter()
                        binding.adminControleProductRvSizes.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)
                        binding.adminControleProductRvSizes.adapter = sizeAdapter
                        sizeAdapter.submitList(product?.sizes)

                    }
                }
            }
        }


        return binding.root
    }

    fun showImageDialog(context: Context, productSubImage: ProductSubImage? = null, prdtId: Long = -1, editAction: (productSubImage: ProductSubImage)->Unit = {}, addAction: ()->Unit = {}) {
        val inflater = LayoutInflater.from(context)
        dialogImage = DialogImagesBinding.inflate(inflater)

        val builder = AlertDialog.Builder(context)
        val view = dialogImage.root


        builder.setView(view)

        val dialog = builder.create()


        dialogImage.dialogEditBtn.root.visibility = View.GONE



        productSubImage?.let {
            dialogImage.dialogEditBtn.root.visibility = View.VISIBLE
            dialogImage.dialogAddBtn.root.visibility = View.GONE

            Picasso.get().load(it.imageUrl).into(dialogImage.dialogImage)
            dialogImage.dialogEditBtn.root.setOnClickListener {

                editAction(productSubImage)

                dialog.dismiss()
            }

        } ?: run {
            val url = dialogImage.dialogEtImageUrl.text.toString()
        }

        dialogImage.dialogEtImageUrl.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                p0?.let {
                    if (it.length >= 0){
                        try {
                            Picasso.get().load(it.toString()).placeholder(R.drawable.upload_ic).into(dialogImage.dialogImage)
                        } catch (e: Exception) {
                            Toast.makeText(requireContext(), "provide valid url", Toast.LENGTH_SHORT).show()
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