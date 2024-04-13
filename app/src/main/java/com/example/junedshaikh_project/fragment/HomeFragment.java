package com.example.junedshaikh_project.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.junedshaikh_project.R;
import com.example.junedshaikh_project.adapter.ProductAdapter;
import com.example.junedshaikh_project.databinding.FragmentHomeBinding;
import com.example.junedshaikh_project.db.Product;
import com.example.junedshaikh_project.db.ProductDatabase;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private ProductAdapter productAdapter;
    private FirebaseFirestore db;
    private ProductDatabase database;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        db = FirebaseFirestore.getInstance();
        database = ProductDatabase.getDatabase(requireContext());
        ProgressBar progressBar = binding.progressBar; // Reference to the ProgressBar in your layout

        db.collection("Products")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    List<Product> productList = new ArrayList<>();
                    for (DocumentSnapshot document : queryDocumentSnapshots.getDocuments()) {
                        String name = document.getString("name") != null ? document.getString("name") : "";
                        String description = document.getString("description") != null ? document.getString("description") : "";
                        String details = document.getString("details") != null ? document.getString("details") : "";
                        double price = document.getDouble("price") != null ? document.getDouble("price") : 0.0;
                        String imageUrl = document.getString("imageUrl") != null ? document.getString("imageUrl") : "";
                        Product product = new Product(name, description, details, price, imageUrl);
                        productList.add(product);
                        Log.d("HomeFragment", productList.toString());
                    }
                    binding.productRv.setLayoutManager(new LinearLayoutManager(getContext()));
                    productAdapter = new ProductAdapter(productList, new ProductAdapter.OnClickCart() {
                        @Override
                        public void onClick(Product product) {
                            new Thread(() -> {
                                product.setInCart(true);
                                database.getProductDao().insert(product);
                                requireActivity().runOnUiThread(() ->
                                        Toast.makeText(requireContext(), "Product added to cart", Toast.LENGTH_SHORT).show());
                            }).start();
                        }
                    }, product -> NavHostFragment.findNavController(HomeFragment.this)
                            .navigate(R.id.action_homeFragment_to_checkoutFragment), new ProductAdapter.OnClickImage() {
                        @Override
                        public void onClick(Product product) {
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("product", product);
                            NavHostFragment.findNavController(HomeFragment.this)
                                    .navigate(R.id.action_homeFragment_to_detailsFragment, bundle);
                        }
                    });
                    binding.productRv.setAdapter(productAdapter);
                    progressBar.setVisibility(View.GONE);
                })
                .addOnFailureListener(Throwable::printStackTrace);
        binding.searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText != null) {
                    if (productAdapter != null) {
                        productAdapter.filterList(newText);
                    }
                }
                return true;
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
