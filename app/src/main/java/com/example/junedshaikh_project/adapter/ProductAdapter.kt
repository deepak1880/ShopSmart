package com.example.junedshaikh_project.adapter

import android.graphics.Rect
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.junedshaikh_project.databinding.ProductListingItemBinding
import com.example.junedshaikh_project.model.Product

class ProductAdapter(
    private val products: List<Product>,
    private var onClickCart: (Product) -> Unit,
    private var onClickBuy: (Product) -> Unit
) :
    RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val binding =
            ProductListingItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = products[position]
        holder.bind(product)
    }

    override fun getItemCount(): Int {
        return products.size
    }

    inner class ProductViewHolder(private val binding: ProductListingItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(product: Product) {
            binding.apply {
                productNameTv.text = product.name
                productDescTv.text = product.description
                productPriceTv.text = "$${product.price}"
                productImageView.load(product.imageUrl)
                addCartBtn.setOnClickListener {
                    onClickCart.invoke(product)
                }
                buyNowBtn.setOnClickListener {
                   onClickBuy.invoke(product)
                }
            }
        }
    }
}

class StaggeredGridSpacingItemDecoration(
    private val spacing: Int,
    private val spanCount: Int
) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val position = parent.getChildAdapterPosition(view)
        val column = position % spanCount

        outRect.top = spacing
        outRect.bottom = spacing
        outRect.left = spacing
        outRect.right = spacing

        // Add spacing between columns for StaggeredGridLayoutManager
        if (spanCount > 1) {
            outRect.left = spacing - column * spacing / spanCount
            outRect.right = (column + 1) * spacing / spanCount
        }
    }

}
