package com.example.junedshaikh_project.fragment

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.junedshaikh_project.adapter.ProductAdapter
import com.example.junedshaikh_project.databinding.FragmentHomeBinding
import com.example.junedshaikh_project.model.Product
import com.example.thefoodcoast.base.BaseFragment
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore

class HomeFragment : BaseFragment<FragmentHomeBinding>() {

    private var productAdapter: ProductAdapter? = null
    val db = Firebase.firestore
    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentHomeBinding.inflate(inflater, container, false)

    override fun initialSetUp() {
        db.collection("Products")
            .get()
            .addOnSuccessListener { result ->
                val productList = mutableListOf<Product>()
                for (document in result) {
                    val name = document.getString("name") ?: ""
                    val description = document.getString("description") ?: ""
                    val price = document.getDouble("price") ?: 0.0
                    val imageUrl = document.getString("imageUrl") ?: ""
                    val product = Product(name, description, price, imageUrl)
                    productList.add(product)
                    Log.d("HomeFragment", productList.toString())
                }
                binding.productRv.layoutManager = LinearLayoutManager(context)
                productAdapter = ProductAdapter(productList, onClickCart = {
                    // Handle onClickCart action
                }, onClickBuy = {
                    // Handle onClickBuy action
                })
                binding.productRv.adapter = productAdapter
            }
            .addOnFailureListener { exception ->
                Log.e("HomeFragment", "Error getting documents.")
            }

    }
}

