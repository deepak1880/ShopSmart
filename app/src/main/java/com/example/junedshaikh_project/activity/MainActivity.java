package com.example.junedshaikh_project.activity;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.example.junedshaikh_project.R;
import com.example.junedshaikh_project.databinding.ActivityMainBinding;
import com.example.junedshaikh_project.db.ProductDao;
import com.example.junedshaikh_project.db.ProductDatabase;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private ProductDao productDao;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        BottomNavigationView navView = binding.bottomNavigation;

        NavController navController = ((NavHostFragment) getSupportFragmentManager()
                .findFragmentById(R.id.fragentContainer)).getNavController();

        productDao = ProductDatabase.getDatabase(this).getProductDao();
        LiveData<Integer> productCountLiveData = productDao.getProductCount();
        productCountLiveData.observe(this, count -> {
            navView.getOrCreateBadge(R.id.cartFragment).setVisible(true);
            navView.getOrCreateBadge(R.id.cartFragment).setNumber(count);
        });

        NavigationUI.setupWithNavController(navView, navController);
    }
}
