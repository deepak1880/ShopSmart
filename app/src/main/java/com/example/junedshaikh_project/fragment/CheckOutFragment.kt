package com.example.junedshaikh_project.fragment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.junedshaikh_project.R
import com.example.junedshaikh_project.databinding.FragmentCheckOutBinding
import com.example.thefoodcoast.base.BaseFragment

class CheckOutFragment : BaseFragment<FragmentCheckOutBinding>() {
    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentCheckOutBinding.inflate(layoutInflater, container, false)

    override fun initialSetUp() {
        super.initialSetUp()

        binding.commonHeaderLayout.commonHeaderBackArrow.setOnClickListener {
            requireActivity().onBackPressed()
        }
        binding.commonHeaderLayout.commonHeaderTitleTextView.text = getText(R.string.checkout_page)

        val fullName = binding.etFullName.text.toString().trim()
        val ageInput = binding.etAge.text.toString().trim()
        val age = if (ageInput.isNotEmpty()) ageInput.toIntOrNull() else null
        val phoneNumber = binding.etPhoneNumber.text.toString().trim()
        val address = binding.etAddress.text.toString().trim()
        val pinCode = binding.etPinCode.text.toString().trim()

        fun validateUserInput(): Boolean {
            var hasError = false

            val phoneRegex = "^\\d{10}$".toRegex()
            if (phoneNumber.isNotEmpty() && !phoneRegex.matches(phoneNumber)) {
                binding.etPhoneNumber.error = "Invalid phone number format"
                hasError = true
            } else {
                binding.etPhoneNumber.error = null
            }

            val pinRegex = "^\\d{6}$".toRegex()
            if (pinCode.isNotEmpty() && !pinRegex.matches(pinCode)) {
                binding.etPinCode.error = "Invalid pin code format"
                hasError = true
            } else {
                binding.etPinCode.error = null
            }

            if (age != null && age < 1) {
                binding.etAge.error = "Please enter a valid age"
                hasError = true
            } else {
                binding.etAge.error = null
            }
            return !hasError
        }

        binding.btnSubmit.setOnClickListener {
            if (validateUserInput()) {
                findNavController().navigate(
                    R.id.action_checkoutFragment_to_thankyouFragment
                )
            } else {
                // User input has errors, all error messages should be set in validateUserInput()
            }
        }
    }
}