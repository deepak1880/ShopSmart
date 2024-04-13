package com.example.junedshaikh_project.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.junedshaikh_project.R;
import com.example.junedshaikh_project.adapter.CartAdapter;
import com.example.junedshaikh_project.databinding.FragmentCartBinding;
import com.example.junedshaikh_project.db.Product;
import com.example.junedshaikh_project.db.ProductDatabase;

import java.util.List;

public class CartFragment extends Fragment {

    private CartAdapter cartAdapter;
    private FragmentCartBinding binding;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        binding = FragmentCartBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.commonHeaderLayout.commonHeaderTitleTextView.setText(getString(R.string.my_cart));
        binding.commonHeaderLayout.commonHeaderBackArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requireActivity().onBackPressed();
            }
        });

        binding.myCartListsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        cartAdapter = new CartAdapter(product -> new Thread(() -> {
            product.setInCart(false);
            ProductDatabase.getDatabase(requireContext()).getProductDao().delete(product);
        }).start(), productQnt -> new Thread(() -> {
            ProductDatabase.getDatabase(requireContext()).getProductDao().updateQuantity(productQnt);
        }).start());

        binding.myCartListsRecyclerView.setAdapter(cartAdapter);
        ProductDatabase.getDatabase(requireContext()).getProductDao().getCartProducts().observe(getViewLifecycleOwner(), new androidx.lifecycle.Observer<List<Product>>() {
            @Override
            public void onChanged(List<Product> products) {
                cartAdapter.submitList(products);
                updateUIVisibility();
            }
        });
        updateTotalPrice();

        binding.orderNowButton.setOnClickListener(v -> NavHostFragment.findNavController(CartFragment.this).navigate(R.id.action_cartFragment_to_checkoutFragment));
    }

    private void updateTotalPrice() {
        ProductDatabase.getDatabase(requireContext()).getProductDao().getTotalPrice().observe(getViewLifecycleOwner(), totalPrice -> {
            if (totalPrice != null) {
                binding.totalAmountTextView.setText(getString(R.string.total_price, totalPrice));
            }
        });
    }

    private void updateUIVisibility() {
        if (cartAdapter.isCartEmpty()) {
            binding.noDataFoundLayout.setVisibility(View.VISIBLE);
            binding.orderNowButton.setVisibility(View.GONE);
            binding.totalAmountTextView.setVisibility(View.GONE);
        } else {
            binding.noDataFoundLayout.setVisibility(View.GONE);
            binding.orderNowButton.setVisibility(View.VISIBLE);
            binding.totalAmountTextView.setVisibility(View.VISIBLE);
        }
    }
}
