package com.example.junedshaikh_project.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.junedshaikh_project.R
import com.example.junedshaikh_project.adapter.ProductAdapter
import com.example.junedshaikh_project.databinding.FragmentHomeBinding
import com.example.junedshaikh_project.db.Product
import com.example.junedshaikh_project.db.ProductDatabase
import com.example.thefoodcoast.base.BaseFragment
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class HomeFragment : BaseFragment<FragmentHomeBinding>() {

    private var productAdapter: ProductAdapter? = null
    val db = Firebase.firestore
    private var database: ProductDatabase? = null
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
                    CoroutineScope(Dispatchers.IO).launch {
                        it.inCart = true
                        ProductDatabase.getDatabase(requireContext()).getNoteDao().insert(it)
                    }

                }, onClickBuy = {
                    // Handle onClickBuy action
                }, onClickImage = {
                    val bundle = Bundle()
                    bundle.putSerializable("product", it)
                    findNavController().navigate(
                        R.id.action_homeFragment_to_detailsFragment,
                        bundle
                    )
                })
                binding.productRv.adapter = productAdapter
            }
            .addOnFailureListener { exception ->
                exception.printStackTrace()
            }


    }
}

