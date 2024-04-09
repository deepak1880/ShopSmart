package com.example.junedshaikh_project.fragment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.junedshaikh_project.R
import com.example.junedshaikh_project.adapter.ProductAdapter
import com.example.junedshaikh_project.adapter.StaggeredGridSpacingItemDecoration
import com.example.junedshaikh_project.databinding.FragmentHomeBinding
import com.example.junedshaikh_project.model.Product
import com.example.thefoodcoast.base.BaseFragment

class HomeFragment : BaseFragment<FragmentHomeBinding>() {

    private var productAdapter: ProductAdapter? = null
    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentHomeBinding.inflate(inflater, container, false)

    override fun initialSetUp() {

        binding.productRv.layoutManager =
            StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
        productAdapter = ProductAdapter(getDummyProductList(), onClickCart = { product ->
            // Handle onClickCart action
        }, onClickBuy = { product ->
            // Handle onClickBuy action
        })
        binding.productRv.adapter = productAdapter
        val spacingInPixels = resources.getDimensionPixelSize(R.dimen.dimens_5dp)
        val spanCount = 2
        val decoration = StaggeredGridSpacingItemDecoration(spacingInPixels, spanCount)
        binding.productRv.addItemDecoration(decoration)
    }

    private fun getDummyProductList(): List<Product> {
        return listOf(
            Product("Product 1", "Description 1", 10.0, "image_url_1"),
            Product("Product 2", "Description 2", 20.0, "image_url_2"),
            Product("Product 3", "Description 3", 30.0, "image_url_3"),
        )
    }
}

