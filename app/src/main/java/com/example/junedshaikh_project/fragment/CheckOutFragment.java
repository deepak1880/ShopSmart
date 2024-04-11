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

        binding.submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateInputs()) {
                    Bundle bundle = new Bundle();
                    bundle.putString("name", binding.nameEditText.getText().toString().trim());
                    NavController navController = Navigation.findNavController(requireActivity(), R.id.fragentContainer);
                    navController.navigate(R.id.action_checkoutFragment_to_thankyouFragment,bundle);
                }
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

        return valid;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null; // Release the binding
    }
}

