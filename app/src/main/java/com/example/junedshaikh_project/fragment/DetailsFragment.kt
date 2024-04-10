package com.example.junedshaikh_project.fragment

import android.view.LayoutInflater
import android.view.ViewGroup
import coil.load
import com.example.junedshaikh_project.R
import com.example.junedshaikh_project.databinding.FragmentDetailsBinding
import com.example.junedshaikh_project.db.Product
import com.example.thefoodcoast.base.BaseFragment

class DetailsFragment : BaseFragment<FragmentDetailsBinding>() {
    private var product: Product? = null
    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentDetailsBinding.inflate(inflater, container, false)

    override fun initialSetUp() {
        super.initialSetUp()

        binding.commonHeaderLayout.commonHeaderBackArrow.setOnClickListener {
            requireActivity().onBackPressed()
        }
        binding.commonHeaderLayout.commonHeaderTitleTextView.text =
            getString(R.string.product_details)

        arguments?.let {
            product = it.getSerializable("product") as Product
            setProductDetails(product!!)
        }
    }

    private fun setProductDetails(product: Product) {
        binding.apply {
            binding.productNameTv.text = product.name
            binding.productDescTv.text = product.description
            binding.productPriceTv.text = "$${product.price}"
            binding.productImageView.load(product.imageUrl)
        }
    }
}