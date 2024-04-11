package com.example.junedshaikh_project.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.bumptech.glide.Glide;
import com.example.junedshaikh_project.R;
import com.example.junedshaikh_project.databinding.FragmentDetailsBinding;
import com.example.junedshaikh_project.db.Product;
import com.example.junedshaikh_project.db.ProductDao;
import com.example.junedshaikh_project.db.ProductDatabase;

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
        binding.addCartBtn.setOnClickListener(v -> addToCart());

        binding.buyNowBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavController navController = Navigation.findNavController(requireActivity(), R.id.fragentContainer);
                navController.navigate(R.id.action_detailsFragment_to_checkoutFragment);
            }
        });
        //return view;
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(requireContext(),
                R.array.quantity_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        binding.spinnerQuantity.setAdapter(adapter);

        binding.spinnerQuantity.setSelection(product.getQuantity() - 1);

        binding.spinnerQuantity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                product.setQuantity(position + 1);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });

    }

    private void addToCart() {
        if (product != null) {
            new Thread(() -> {
                ProductDao productDao = ProductDatabase.getDatabase(requireContext()).getProductDao();
                productDao.insert(product);

                requireActivity().runOnUiThread(() ->
                        Toast.makeText(requireContext(), "Product added to cart", Toast.LENGTH_SHORT).show());
            }).start();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}

