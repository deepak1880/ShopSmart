package com.example.junedshaikh_project.fragment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.junedshaikh_project.R
import com.example.junedshaikh_project.adapter.CartAdapter
import com.example.junedshaikh_project.databinding.FragmentCartBinding
import com.example.junedshaikh_project.db.ProductDatabase
import com.example.thefoodcoast.base.BaseFragment

class CartFragment : BaseFragment<FragmentCartBinding>() {

    private val cartAdapter: CartAdapter = CartAdapter()
    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentCartBinding.inflate(inflater, container, false)

    override fun initialSetUp() {
        super.initialSetUp()

        binding.commonHeaderLayout.commonHeaderTitleTextView.text = getString(R.string.my_cart)
        binding.commonHeaderLayout.commonHeaderBackArrow.setOnClickListener {
            requireActivity().onBackPressed()
        }

        binding.myCartListsRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.myCartListsRecyclerView.adapter = cartAdapter


        ProductDatabase.getDatabase(requireContext()).getNoteDao().getCartProducts()
            .observe(viewLifecycleOwner) { products ->
                cartAdapter.submitList(products)
            }
    }
}