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
import androidx.recyclerview.widget.RecyclerView;
import com.example.junedshaikh_project.R;
import com.example.junedshaikh_project.adapter.ProductAdapter;
import com.example.junedshaikh_project.databinding.FragmentHomeBinding;
import com.example.junedshaikh_project.db.Product;
import com.example.junedshaikh_project.db.ProductDatabase;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

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

        db.collection("Products")
            .get()
            .addOnSuccessListener(queryDocumentSnapshots -> {
                List<Product> productList = new ArrayList<>();
                for (DocumentSnapshot document : queryDocumentSnapshots.getDocuments()) {
                String name = document.getString("name") != null ? document.getString("name") : "";
                String description = document.getString("description") != null ? document.getString("description") : "";
                double price = document.getDouble("price") != null ? document.getDouble("price") : 0.0;
                String imageUrl = document.getString("imageUrl") != null ? document.getString("imageUrl") : "";
                Product product = new Product(name, description, price, imageUrl);
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
            })
            .addOnFailureListener(Throwable::printStackTrace);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
