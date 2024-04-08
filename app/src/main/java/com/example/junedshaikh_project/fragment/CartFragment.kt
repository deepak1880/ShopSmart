package com.example.junedshaikh_project.fragment

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.junedshaikh_project.databinding.FragmentCartBinding
import com.example.thefoodcoast.base.BaseFragment

class CartFragment : BaseFragment<FragmentCartBinding>() {
    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentCartBinding.inflate(inflater, container, false)

    override fun initialSetUp() {
        super.initialSetUp()
    }
}