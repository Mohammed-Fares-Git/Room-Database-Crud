package com.MohammedFares.ecomerce_project.presentation.admin

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.MohammedFares.ecomerce_project.R
import com.MohammedFares.ecomerce_project.comon.Resource
import com.MohammedFares.ecomerce_project.data.entity.ProductBrand
import com.MohammedFares.ecomerce_project.data.entity.ProductColor
import com.MohammedFares.ecomerce_project.data.entity.ProductSize
import com.MohammedFares.ecomerce_project.data.entity.ProductSubImage
import com.MohammedFares.ecomerce_project.data.entity.ProductType
import com.MohammedFares.ecomerce_project.databinding.AdminProductControlBinding
import com.MohammedFares.ecomerce_project.databinding.DialogBrandBinding
import com.MohammedFares.ecomerce_project.databinding.DialogColorBinding
import com.MohammedFares.ecomerce_project.databinding.DialogImagesBinding
import com.MohammedFares.ecomerce_project.databinding.DialogSizeBinding
import com.MohammedFares.ecomerce_project.databinding.DialogTypeBinding
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
    private lateinit var dialogSize: DialogSizeBinding
    private lateinit var dialogColor: DialogColorBinding
    private lateinit var dialogBrand: DialogBrandBinding
    private lateinit var dialogType: DialogTypeBinding
    private var mainProductId: Long? = null


    //val adminProductControleViewModel: AdminProductControleViewModel by viewModels()
    val adminProductControleViewModel: AdminProductControleViewModel by hiltNavGraphViewModels(R.id.administration_graph_xml)

    val args: AdminProductControlArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = AdminProductControlBinding.inflate(inflater, container, false)

        mainProductId = args.productId ?: 1


        adminProductControleViewModel.getProductById(mainProductId!!)
        viewLifecycleOwner.lifecycleScope.launch {
            adminProductControleViewModel.productStateFlow.collect {
                when (it) {
                    is Resource.Empty -> Unit
                    is Resource.Error -> Unit
                    is Resource.Loading -> Unit
                    is Resource.Success -> {
                        val product = it.data

                        product?.let {
                            Picasso.get().load(it.product.mainImage)
                                .placeholder(R.drawable.no_connectio_50)
                                .into(binding.adminControleProductMainImage)
                        }

                        binding.adminControleProductName.text = product?.product?.productName
                        binding.adminControleDescTv.text = product?.product?.productDesc
                        binding.adminControleQntTv.text = "${ product?.product?.quantity } ${requireActivity().getText(R.string.moroccan_dirham_acronym)}"
                        binding.adminControleTvType.text = product?.type?.typeName
                        Picasso.get().load(product?.type?.typeImage).resize(200,200).into(binding.adminControleImgType)


                        val imageAdapter = AdminSubImagesAdapter({
                            showImageDialog(
                                context = requireContext(),
                                productSubImage = it,
                                editAction = {
                                    adminProductControleViewModel.editPeodactImage(it)
                                })
                        }, {
                            adminProductControleViewModel.deletePeodactImage(it)
                        })

                        binding.adminControleEditProductImages.root.setOnClickListener {
                            showImageDialog(
                                context = requireContext(),
                                prdtId = product!!.product.productId,
                                addAction = {
                                    adminProductControleViewModel.addProdactImage(it)
                                })
                        }
                        binding.adminControleProductRvImages.layoutManager = LinearLayoutManager(
                            requireContext(),
                            LinearLayoutManager.HORIZONTAL,
                            false
                        )
                        binding.adminControleProductRvImages.adapter = imageAdapter
                        imageAdapter.submitList(product?.subImages)


                        val colorAdapter = AdminColorAdapter(
                            onClickColor = {
                                showColorDialog(
                                    context = requireContext(),
                                    productColor = it
                                )
                            }
                        )
                        binding.adminControleEditProductColors.root.setOnClickListener {
                            showColorDialog(
                                context = requireContext(),
                                prdtId = product!!.product.productId
                            )
                        }
                        binding.adminControleProductRvColors.layoutManager = LinearLayoutManager(
                            requireContext(),
                            LinearLayoutManager.HORIZONTAL,
                            false
                        )
                        binding.adminControleProductRvColors.adapter = colorAdapter
                        colorAdapter.submitList(product?.colors)


                        val sizeAdapter = AdminSizesAdapter(
                            onClickSize = {
                                showSizeDialog(
                                    context = requireContext(),
                                    productSize = it

                                )
                            }
                        )
                        binding.adminControleEditProductSizes.root.setOnClickListener {
                            showSizeDialog(
                                context = requireContext(),
                                prdtId = product!!.product.productId,
                                addAction = {
                                    Toast.makeText(requireContext(), "add size", Toast.LENGTH_SHORT)
                                        .show()
                                })
                        }
                        binding.adminControleProductRvSizes.layoutManager = LinearLayoutManager(
                            requireContext(),
                            LinearLayoutManager.HORIZONTAL,
                            false
                        )
                        binding.adminControleProductRvSizes.adapter = sizeAdapter
                        sizeAdapter.submitList(product?.sizes)


                        binding.adminControleEditProductBrand.root.setOnClickListener {
                            showBrandDialog(
                                context = requireContext(),
                                productBrand = product!!.brand
                            )
                        }

                        binding.adminControleEditProductType.root.setOnClickListener {
                            showTypeDialog(
                                context = requireContext(),
                                productType = product!!.type
                            )
                        }

                    }
                }
            }
        }


        return binding.root
    }

    fun showImageDialog(
        context: Context,
        productSubImage: ProductSubImage? = null,
        prdtId: Long = -1,
        editAction: (productSubImage: ProductSubImage) -> Unit = {},
        addAction: (productSubImage: ProductSubImage) -> Unit = {}
    ) {
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
            var image: ProductSubImage? = null
            mainProductId?.let {
                image = ProductSubImage(productId = it, imageUrl = url)
            }

            image?.let {
                addAction(it)
            }
            dialog.dismiss()
        }

        dialogImage.dialogEtImageUrl.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                p0?.let {
                    if (it.length >= 0) {
                        try {
                            Picasso.get().load(it.toString()).placeholder(R.drawable.upload_ic)
                                .into(dialogImage.dialogImage)
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


    fun showSizeDialog(
        context: Context,
        productSize: ProductSize? = null,
        prdtId: Long = -1,
        editAction: (productSize: ProductSize) -> Unit = {},
        addAction: () -> Unit = {}
    ) {
        val inflater = LayoutInflater.from(context)
        dialogSize = DialogSizeBinding.inflate(inflater)

        val builder = AlertDialog.Builder(context)
        val view = dialogSize.root


        builder.setView(view)

        val dialog = builder.create()


        dialogSize.dialogEditBtn.root.visibility = View.GONE



        productSize?.let {
            dialogSize.dialogEditBtn.root.visibility = View.VISIBLE
            dialogSize.dialogAddBtn.root.visibility = View.GONE

            dialogSize.siveTv.sizeName.text = it.sizeName
            dialogSize.dialogEditBtn.root.setOnClickListener {

                editAction(productSize)

                dialog.dismiss()
            }

        }

        dialogSize.dialogEtImageUrl.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                p0?.let {
                    dialogSize.siveTv.sizeName.text = it
                }

            }

            override fun afterTextChanged(p0: Editable?) {
            }

        })

        dialog.window?.attributes?.windowAnimations = R.style.DialogAnimation
        dialog.window?.setBackgroundDrawableResource(R.drawable.auth_forms_bg)


        dialog.show()
    }


    fun showColorDialog(
        context: Context,
        productColor: ProductColor? = null,
        prdtId: Long = -1,
        editAction: (productColor: ProductColor) -> Unit = {},
        addAction: () -> Unit = {}
    ) {
        val inflater = LayoutInflater.from(context)
        dialogColor = DialogColorBinding.inflate(inflater)

        val builder = AlertDialog.Builder(context)
        val view = dialogColor.root


        builder.setView(view)

        val dialog = builder.create()


        dialogColor.dialogEditBtn.root.visibility = View.GONE



        productColor?.let {
            dialogColor.dialogEditBtn.root.visibility = View.VISIBLE
            dialogColor.dialogAddBtn.root.visibility = View.GONE

            dialogColor.dialogEtColorName.text =
                Editable.Factory.getInstance().newEditable(it.colorName)
            dialogColor.dialogEtColorHex.text =
                Editable.Factory.getInstance().newEditable(it.colorHexCode)
            dialogColor.colorLayOut.colorName.text = it.colorName
            try {
                dialogColor.colorLayOut.colorHex.setBackgroundColor(Color.parseColor(it.colorHexCode))
            } catch (e: Exception) {
                Toast.makeText(
                    requireContext(),
                    requireActivity().getText(R.string.invalid_hex_code),
                    Toast.LENGTH_SHORT
                ).show()
            }
            dialogColor.dialogEditBtn.root.setOnClickListener {

                editAction(productColor)

                dialog.dismiss()
            }

        }

        dialogColor.dialogEtColorHex.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                p0?.let {
                    if (it.length >= 0) {
                        try {
                            dialogColor.colorLayOut.colorHex.setBackgroundColor(Color.parseColor(it.toString()))
                        } catch (e: Exception) {
                            Toast.makeText(
                                requireContext(),
                                requireActivity().getText(R.string.invalid_hex_code),
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }

            }

            override fun afterTextChanged(p0: Editable?) {
            }

        })

        dialogColor.dialogEtColorName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                p0.let {
                    dialogColor.colorLayOut.colorName.text = p0
                }
            }

            override fun afterTextChanged(p0: Editable?) {}

        })

        dialog.window?.attributes?.windowAnimations = R.style.DialogAnimation
        dialog.window?.setBackgroundDrawableResource(R.drawable.auth_forms_bg)


        dialog.show()
    }


    fun showBrandDialog(
        context: Context,
        productBrand: ProductBrand? = null,
        prdtId: Long = -1,
        editAction: (productBrand: ProductBrand) -> Unit = {},
        addAction: () -> Unit = {}
    ) {
        val inflater = LayoutInflater.from(context)
        dialogBrand = DialogBrandBinding.inflate(inflater)

        val builder = AlertDialog.Builder(context)
        val view = dialogBrand.root


        builder.setView(view)

        val dialog = builder.create()


        dialogBrand.dialogEditBtn.root.visibility = View.GONE



        productBrand?.let {
            dialogBrand.dialogEditBtn.root.visibility = View.VISIBLE
            dialogBrand.dialogAddBtn.root.visibility = View.GONE

            dialogBrand.dialogEtBrandName.text =
                Editable.Factory.getInstance().newEditable(it.brandName)
            dialogBrand.dialogEtBrandUrl.text =
                Editable.Factory.getInstance().newEditable(it.brandImage)
            dialogBrand.brandLayout.brandName.text = it.brandName
            try {
                Picasso.get().load(it.brandImage).into(dialogBrand.brandLayout.brandeImage)
            } catch (e: Exception) {
                Toast.makeText(
                    requireContext(),
                    requireActivity().getText(R.string.provide_valid_url),
                    Toast.LENGTH_SHORT
                ).show()
            }
            dialogBrand.dialogEditBtn.root.setOnClickListener {

                editAction(productBrand)

                dialog.dismiss()
            }

        }

        dialogBrand.dialogEtBrandName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                p0?.let {
                    if (it.length >= 0) {
                        dialogBrand.brandLayout.brandName.text = it.toString()
                    }
                }

            }

            override fun afterTextChanged(p0: Editable?) {
            }

        })

        dialogBrand.dialogEtBrandUrl.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                p0.let {
                    try {
                        Picasso.get().load(it.toString()).into(dialogBrand.brandLayout.brandeImage)
                    } catch (e: Exception) {
                        Toast.makeText(
                            requireContext(),
                            requireActivity().getText(R.string.provide_valid_url),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }

            override fun afterTextChanged(p0: Editable?) {}

        })

        dialog.window?.attributes?.windowAnimations = R.style.DialogAnimation
        dialog.window?.setBackgroundDrawableResource(R.drawable.auth_forms_bg)


        dialog.show()
    }


    fun showTypeDialog(
        context: Context,
        productType: ProductType? = null,
        editAction: (productType: ProductType) -> Unit = {}
    ) {
        val inflater = LayoutInflater.from(context)
        dialogType = DialogTypeBinding.inflate(inflater)

        val builder = AlertDialog.Builder(context)
        val view = dialogType.root


        builder.setView(view)

        val dialog = builder.create()


        dialogType.dialogEditBtn.root.visibility = View.GONE



        productType?.let {
            dialogType.dialogEditBtn.root.visibility = View.VISIBLE
            dialogType.dialogAddBtn.root.visibility = View.GONE

            dialogType.dialogEtTyptName.text =
                Editable.Factory.getInstance().newEditable(it.typeName)
            dialogType.dialogEtTypeUrl.text =
                Editable.Factory.getInstance().newEditable(it.typeImage)
            dialogType.typeLayout.typeName.text = it.typeName
            try {
                Picasso.get().load(it.typeImage).into(dialogType.typeLayout.typeImage)
            } catch (e: Exception) {
                Toast.makeText(
                    requireContext(),
                    requireActivity().getText(R.string.provide_valid_url),
                    Toast.LENGTH_SHORT
                ).show()
            }
            dialogType.dialogEditBtn.root.visibility = View.GONE
            dialogType.dialogAddBtn.root.visibility = View.VISIBLE
            dialogType.dialogEditBtn.root.setOnClickListener {
                val type_name = dialogType.dialogEtTyptName.text.toString()
                val type_url = dialogType.dialogEtTypeUrl.text.toString()
                val productTypeCopie = productType.copy(
                    typeName = type_name,
                    typeImage = type_url
                )
                adminProductControleViewModel.editProductType(productTypeCopie)
                dialog.dismiss()
            }

        }

        dialogType.dialogEtTyptName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                p0?.let {
                    if (it.length >= 0) {
                        dialogType.typeLayout.typeName.text = it.toString()
                    }
                }

            }

            override fun afterTextChanged(p0: Editable?) {
            }

        })

        dialogType.dialogEtTypeUrl.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                p0.let {
                    try {
                        Picasso.get().load(it.toString()).into(dialogType.typeLayout.typeImage)
                    } catch (e: Exception) {
                        Toast.makeText(
                            requireContext(),
                            requireActivity().getText(R.string.provide_valid_url),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }

            override fun afterTextChanged(p0: Editable?) {}

        })

        dialog.window?.attributes?.windowAnimations = R.style.DialogAnimation
        dialog.window?.setBackgroundDrawableResource(R.drawable.auth_forms_bg)


        dialog.show()
    }


}