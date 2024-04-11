package com.example.junedshaikh_project.fragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.junedshaikh_project.R
import com.example.junedshaikh_project.adapter.CartAdapter
import com.example.junedshaikh_project.databinding.FragmentCartBinding
import com.example.junedshaikh_project.db.ProductDatabase
import com.example.thefoodcoast.base.BaseFragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CartFragment : BaseFragment<FragmentCartBinding>() {

    private var cartAdapter: CartAdapter? = null
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
        cartAdapter = CartAdapter(
            onClick = {
                CoroutineScope(Dispatchers.IO).launch {
                    it.isInCart = true
                    ProductDatabase.getDatabase(requireContext()).getProductDao().delete(it)
                }
            })
        binding.myCartListsRecyclerView.adapter = cartAdapter
        ProductDatabase.getDatabase(requireContext()).getProductDao().getCartProducts()
            .observe(viewLifecycleOwner) { products ->
                cartAdapter!!.submitList(products)
                updateUIVisibility()
            }

        binding.orderNowButton.setOnClickListener {
            findNavController().navigate(
                R.id.action_cartFragment_to_checkoutFragment
            )

        }

    }

    private fun updateUIVisibility() {
        if (cartAdapter!!.isCartEmpty()) {
            binding.noDataFoundLayout.visibility = View.VISIBLE
            binding.orderNowButton.visibility = View.GONE
        } else {
            binding.noDataFoundLayout.visibility = View.GONE
            binding.orderNowButton.visibility = View.VISIBLE
        }
    }
}