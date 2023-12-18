package com.MohammedFares.ecomerce_project.presentation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.MohammedFares.ecomerce_project.R
import com.MohammedFares.ecomerce_project.comon.Constantes
import com.MohammedFares.ecomerce_project.comon.Resource
import com.MohammedFares.ecomerce_project.data.entity.ProductLike
import com.MohammedFares.ecomerce_project.databinding.ActivityProductBinding
import com.MohammedFares.ecomerce_project.domain.model.SelectebleColor
import com.MohammedFares.ecomerce_project.domain.model.SelectebleSize
import com.MohammedFares.ecomerce_project.presentation.adapters.ColorAdapter
import com.MohammedFares.ecomerce_project.presentation.adapters.ColorAdapter2
import com.MohammedFares.ecomerce_project.presentation.adapters.SizeAdapter
import com.MohammedFares.ecomerce_project.presentation.adapters.SizeAdapter2
import com.denzcoskun.imageslider.models.SlideModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class ProductActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProductBinding
    private lateinit var intent: Intent
    private lateinit var selectebleColors: List<SelectebleColor>
    private lateinit var selectebleSizes: List<SelectebleSize>
    private lateinit var colorAdapter: ColorAdapter2
    private lateinit var sizeAdapter: SizeAdapter2

    val clientId: Long = 1


    val productViewModel: ProductViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductBinding.inflate(layoutInflater)
        setContentView(binding.root)


        intent = getIntent()

        intent?.let {
            if (it.hasExtra(Constantes.PRODUCT_ID_KEY)) {
                val id = it.getLongExtra(Constantes.PRODUCT_ID_KEY,1L)
                productViewModel.getProduct(id)
            }
        }

        colorAdapter = ColorAdapter2  {
            productViewModel.selectColor(it)
        }
        colorAdapter.setColors(emptyList())
        binding.colorsRv.layoutManager = LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)
        binding.colorsRv.adapter = colorAdapter
        sizeAdapter = SizeAdapter2 (ctx = this) {
            productViewModel.selectSize(it)
            Toast.makeText(this@ProductActivity, "clicked ${it}", Toast.LENGTH_SHORT).show()
        }
        sizeAdapter.setSizes(emptyList())
        binding.sizesRv.setHasFixedSize(true)
        binding.sizesRv.layoutManager = LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)
        binding.sizesRv.adapter = sizeAdapter

        lifecycleScope.launch {


            productViewModel.prodctsStateFlow.collect {
                when (it) {
                    is Resource.Empty -> Unit
                    is Resource.Error -> Unit
                    is Resource.Loading -> Unit
                    is Resource.Success -> {
                        val imageList = ArrayList<SlideModel>()

                        it.data?.let {

                            var likes =  emptyList<ProductLike>()

                            likes = it.likes.filter { productLike ->
                                productLike.clientId == clientId
                            }

                            if (likes.size > 0) {
                                binding.isProductLiked.setImageResource(R.drawable.heart_solid_24)
                            } else {
                                binding.isProductLiked.setImageResource(R.drawable.favorite_ic)
                            }
                            binding.isProductLiked.setOnClickListener {view ->
                                if (likes.size > 0) {
                                    productViewModel.removeLike(it.likes[0])
                                } else {
                                    productViewModel.putLike(it.product.productId, clientId)
                                }
                            }



                            it.subImages.forEach {image->
                                imageList.add(SlideModel(image.imageUrl))
                            }

                            binding.slider.setImageList(imageList)
                            binding.productName.text = it.product.productName
                            val colors = it.colors
                            val sizes = it.sizes

                            binding.productDesc.text = it.product.productDesc
                            binding.prix.text = "${ it.product.price } ${getText(R.string.moroccan_dirham_acronym)}"





                            selectebleColors = colors.map {
                                SelectebleColor(it)
                            }

                            selectebleSizes = sizes.map {
                                SelectebleSize(it)
                            }
                            colorAdapter.setColors(selectebleColors)
                            sizeAdapter.setSizes(selectebleSizes)

                        }
                        launch {
                            productViewModel.productScreenState.collect {screenState->
                                //Log.d("ahahhhhhh", screenState.toString())
                                Toast.makeText(this@ProductActivity, "maaaaaa", Toast.LENGTH_SHORT).show()
                                colorAdapter.setColors(emptyList())
                                sizeAdapter.setSizes(emptyList())
                                selectebleColors = selectebleColors.map {color->
                                    if (color.color.colorId == screenState.selctedColor) {
                                        color.isSelected = true
                                    } else {
                                        color.isSelected = false
                                    }
                                    color
                                }
                                //Log.w("ahahhhhhh", newSelectebleColors.toString())



                                selectebleSizes = selectebleSizes.map {size->
                                    if (size.size.sizeId == screenState.selectedSize) {
                                        size.isSelected = true
                                    } else {
                                        size.isSelected = false
                                    }
                                    size
                                }
                                //Log.w("ahahhhhhh", newSelectebleSizes.toString())
                                withContext(Dispatchers.Main) {
                                    colorAdapter.setColors(selectebleColors)
                                    sizeAdapter.setSizes(selectebleSizes)

                                }

                            }
                        }


                    }
                }
            }

        }


    }


}