package com.example.junedshaikh_project.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.junedshaikh_project.R;
import com.example.junedshaikh_project.databinding.FragmentDetailsBinding;
import com.example.junedshaikh_project.db.Product;

public class DetailsFragment extends Fragment {

    private FragmentDetailsBinding binding;
    private Product product;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentDetailsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.commonHeaderLayout.commonHeaderBackArrow.setOnClickListener(v -> requireActivity().onBackPressed());
        binding.commonHeaderLayout.commonHeaderTitleTextView.setText(R.string.product_details);

        Bundle args = getArguments();
        if (args != null) {
            product = (Product) args.getSerializable("product");
            if (product != null) {
                binding.productNameTv.setText(product.getName());
                binding.productDescTv.setText(product.getDescription());
                binding.productPriceTv.setText("$" + product.getPrice());
                Glide.with(requireContext()).load(product.getImageUrl()).into(binding.productImageView);
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}

