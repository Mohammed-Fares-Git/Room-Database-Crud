package com.MohammedFares.ecomerce_project.presentation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.MohammedFares.ecomerce_project.comon.Constantes
import com.MohammedFares.ecomerce_project.comon.Resource
import com.MohammedFares.ecomerce_project.databinding.ActivityProductBinding
import com.denzcoskun.imageslider.models.SlideModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
@AndroidEntryPoint
class ProductActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProductBinding
    private lateinit var intent: Intent

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

        lifecycleScope.launch {
            productViewModel.prodctsStateFlow.collect {
                when (it) {
                    is Resource.Empty -> Unit
                    is Resource.Error -> Unit
                    is Resource.Loading -> Unit
                    is Resource.Success -> {
                        val imageList = ArrayList<SlideModel>()

                        it.data?.let {
                            it.subImages.forEach {image->
                                imageList.add(SlideModel(image.imageUrl))
                            }
                            binding.slider.setImageList(imageList)
                            binding.productName.text = it.product.productName
                        }

                    }
                }
            }
        }

    }


}