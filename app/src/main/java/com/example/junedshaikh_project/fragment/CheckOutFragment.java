package com.example.junedshaikh_project.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.junedshaikh_project.R;
import com.example.junedshaikh_project.databinding.FragmentCheckOutBinding;
import com.example.junedshaikh_project.db.ProductDatabase;

public class CheckOutFragment extends Fragment {

    private FragmentCheckOutBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentCheckOutBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.commonHeaderLayout.commonHeaderBackArrow.setOnClickListener(v -> requireActivity().onBackPressed());
        binding.commonHeaderLayout.commonHeaderTitleTextView.setText(R.string.checkout_page);

        binding.submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateInputs()) {
                    deleteAllProducts();
                    Bundle bundle = new Bundle();
                    bundle.putString("name", binding.nameEditText.getText().toString().trim());
                    NavController navController = Navigation.findNavController(requireActivity(), R.id.fragentContainer);
                    navController.navigate(R.id.action_checkoutFragment_to_thankyouFragment, bundle);
                }
            }

            private void deleteAllProducts() {
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        // Perform database operation on a background thread
                        ProductDatabase.getDatabase(requireContext()).getProductDao().deleteAllProducts();
                    }
                });
                thread.start();
            }

        });

        updateTotalPrice();


    }

    private void updateTotalPrice() {
        ProductDatabase.getDatabase(requireContext()).getProductDao().getTotalPrice().observe(getViewLifecycleOwner(), totalPrice -> {
            if (totalPrice != null) {
                binding.totalAmountTextView.setText(getString(R.string.total_price, totalPrice));
                //binding.totalAmountTextView.setVisibility(View.VISIBLE);
            } else {
                binding.totalAmountTextView.setVisibility(View.GONE);
            }
        });
    }

    private boolean validateInputs() {
        boolean valid = true;

        // Validate name
        if (TextUtils.isEmpty(binding.nameEditText.getText())) {
            binding.nameInputLayout.setError("Name is required");
            valid = false;
        } else {
            binding.nameInputLayout.setError(null);
        }

        // Validate address
        if (TextUtils.isEmpty(binding.addressEditText.getText())) {
            binding.addressInputLayout.setError("Address is required");
            valid = false;
        } else {
            binding.addressInputLayout.setError(null);
        }

        // Validate landmark
        if (TextUtils.isEmpty(binding.landmarkEditText.getText())) {
            binding.landmarkInputLayout.setError("Landmark is required");
            valid = false;
        } else {
            binding.landmarkInputLayout.setError(null);
        }

        // Validate city
        if (TextUtils.isEmpty(binding.cityEditText.getText())) {
            binding.cityInputLayout.setError("City is required");
            valid = false;
        } else {
            binding.cityInputLayout.setError(null);
        }

        // Validate pin code
        if (TextUtils.isEmpty(binding.pinCodeEditText.getText())) {
            binding.pinCodeInputLayout.setError("Pin code is required");
            valid = false;
        } else {
            binding.pinCodeInputLayout.setError(null);
        }
        // Validate card holder name
        if (TextUtils.isEmpty(binding.paymentLayout.cardNameLayout.getEditText().getText().toString())) {
            binding.paymentLayout.cardNameLayout.setError("Name is required");
            valid = false;
        } else {
            binding.paymentLayout.cardNameLayout.setError(null);
        }

        // Validate card number
        if (TextUtils.isEmpty(binding.paymentLayout.cardNumberLayout.getEditText().getText().toString())) {
            binding.paymentLayout.cardNumberLayout.setError("Card Number is required");
            valid = false;
        } else {
            binding.paymentLayout.cardNumberLayout.setError(null);
        }

        // Validate CVV
        if (TextUtils.isEmpty(binding.paymentLayout.cvvLayout.getEditText().getText().toString())) {
            binding.paymentLayout.cvvLayout.setError("CVV is required");
            valid = false;
        } else {
            binding.paymentLayout.cvvLayout.setError(null);
        }

        // Validate expiry date
        if (TextUtils.isEmpty(binding.paymentLayout.expiryDateLayout.getEditText().getText().toString())) {
            binding.paymentLayout.expiryDateLayout.setError("Expiry Date is required");
            valid = false;
        } else {
            binding.paymentLayout.expiryDateLayout.setError(null);
        }

        return valid;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null; // Release the binding
    }
}

