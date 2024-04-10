package com.example.junedshaikh_project.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.junedshaikh_project.databinding.CartItemListingBinding
import com.example.junedshaikh_project.db.Product

class CartAdapter(private val onClick: (Product) -> Unit) :
    RecyclerView.Adapter<CartAdapter.CartViewHolder>() {

    private var productList: List<Product> = emptyList()
    fun submitList(list: List<Product>) {
        productList = list
        notifyDataSetChanged()
    }

    fun isCartEmpty(): Boolean {
        return productList.isEmpty()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val binding =
            CartItemListingBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CartViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        holder.bind(productList[position])
    }

    override fun getItemCount(): Int {
        return productList.size
    }

    inner class CartViewHolder(private val binding: CartItemListingBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.parentCard.setOnClickListener {
                onClick.invoke(productList[adapterPosition])
            }
        }

        fun bind(product: Product) {
            binding.apply {
                productImage.load(product.imageUrl)
                orderItemTitle.text = product.name
                orderItemType.text = product.description
                orderItemCost.text = product.price.toString()
                spinnerQuantity.setSelection(product.quantity - 1)

                spinnerQuantity.onItemSelectedListener =
                    object : AdapterView.OnItemSelectedListener {
                        override fun onItemSelected(
                            parent: AdapterView<*>?,
                            view: View?,
                            position: Int,
                            id: Long
                        ) {
                            product.quantity = position + 1
                        }

                        override fun onNothingSelected(parent: AdapterView<*>?) {
                            // Do nothing
                        }
                    }
            }
        }
    }
}