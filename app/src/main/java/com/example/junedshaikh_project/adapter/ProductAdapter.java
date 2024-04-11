package com.example.junedshaikh_project.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.junedshaikh_project.databinding.ProductListingItemBinding;
import com.example.junedshaikh_project.db.Product;
import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {

    private List<Product> products;
    private OnClickCart onClickCart;
    private OnClickBuy onClickBuy;
    private OnClickImage onClickImage;

    public interface OnClickCart {
        void onClick(Product product);
    }

    public interface OnClickBuy {
        void onClick(Product product);
    }

    public interface OnClickImage {
        void onClick(Product product);
    }

    public ProductAdapter(List<Product> products, OnClickCart onClickCart, OnClickBuy onClickBuy, OnClickImage onClickImage) {
        this.products = products;
        this.onClickCart = onClickCart;
        this.onClickBuy = onClickBuy;
        this.onClickImage = onClickImage;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ProductListingItemBinding binding = ProductListingItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ProductViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Product product = products.get(position);
        holder.bind(product);
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder {

        private ProductListingItemBinding binding;

        public ProductViewHolder(ProductListingItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Product product) {
            binding.productNameTv.setText(product.getName());
            binding.productDescTv.setText(product.getDescription());
            binding.productPriceTv.setText("$" + product.getPrice());

            Glide.with(binding.getRoot())
                    .load(product.getImageUrl())
                    .into(binding.productImageView);
            binding.addCartBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickCart.onClick(product);
                }
            });
            binding.buyNowBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickBuy.onClick(product);
                }
            });
            binding.productImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickImage.onClick(product);
                }
            });
        }
    }
}
