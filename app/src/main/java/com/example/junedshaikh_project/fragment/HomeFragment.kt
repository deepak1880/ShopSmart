package com.example.junedshaikh_project.fragment

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.junedshaikh_project.databinding.FragmentHomeBinding
import com.example.thefoodcoast.base.BaseFragment

class HomeFragment : BaseFragment<FragmentHomeBinding>() {
    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentHomeBinding.inflate(inflater, container, false)

    override fun initialSetUp() {

    }
}

