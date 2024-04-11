package com.example.junedshaikh_project.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.junedshaikh_project.databinding.CartItemListingBinding;
import com.example.junedshaikh_project.db.Product;
import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {

    private List<Product> productList;
    private final OnProductClickListener onClick;

    public CartAdapter(OnProductClickListener onClick) {
        this.onClick = onClick;
    }

    public void submitList(List<Product> list) {
        productList = list;
        notifyDataSetChanged();
    }

    public boolean isCartEmpty() {
        return productList == null || productList.isEmpty();
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CartItemListingBinding binding = CartItemListingBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false);
        return new CartViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        holder.bind(productList.get(position));
    }

    @Override
    public int getItemCount() {
        return productList != null ? productList.size() : 0;
    }

    public interface OnProductClickListener {
        void onItemClick(Product product);
    }

    public class CartViewHolder extends RecyclerView.ViewHolder {

        private final CartItemListingBinding binding;

        public CartViewHolder(CartItemListingBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

            binding.parentCard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClick.onItemClick(productList.get(getAdapterPosition()));
                }
            });
        }

        public void bind(Product product) {
            Glide.with(binding.getRoot())
                    .load(product.getImageUrl())
                    .into(binding.productImage);

            binding.orderItemTitle.setText(product.getName());
            binding.orderItemType.setText("$" + product.getPrice());
            binding.spinnerQuantity.setSelection(product.getQuantity() - 1);

            binding.spinnerQuantity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    product.setQuantity(position + 1);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });
        }
    }
}
