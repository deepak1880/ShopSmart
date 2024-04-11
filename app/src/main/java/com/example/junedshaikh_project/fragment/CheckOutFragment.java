package com.example.junedshaikh_project.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.junedshaikh_project.R;
import com.example.junedshaikh_project.databinding.FragmentCheckOutBinding;

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

        Button btnSubmit = binding.btnSubmit;

        btnSubmit.setOnClickListener(v -> {
            NavHostFragment.findNavController(this).navigate(R.id.action_checkoutFragment_to_thankyouFragment);

//            if (validateUserInput()) {
//                NavHostFragment.findNavController(this).navigate(R.id.action_checkoutFragment_to_thankyouFragment);
//            } else {
//            }
        });
    }

    private boolean validateUserInput() {
        boolean hasError = false;

        String phoneNumber = binding.etPhoneNumber.getText().toString().trim();
        String pinCode = binding.etPinCode.getText().toString().trim();
        String ageInput = binding.etAge.getText().toString().trim();
        Integer age = ageInput.isEmpty() ? null : Integer.parseInt(ageInput);

        if (!phoneNumber.isEmpty() && !phoneNumber.matches("^\\d{10}$")) {
            binding.etPhoneNumber.setError("Invalid phone number format");
            hasError = true;
        } else {
            binding.etPhoneNumber.setError(null);
        }

        if (!pinCode.isEmpty() && !pinCode.matches("^\\d{6}$")) {
            binding.etPinCode.setError("Invalid pin code format");
            hasError = true;
        } else {
            binding.etPinCode.setError(null);
        }

        if (age != null && age < 1) {
            binding.etAge.setError("Please enter a valid age");
            hasError = true;
        } else {
            binding.etAge.setError(null);
        }

        return !hasError;
    }
}
