package com.example.junedshaikh_project.fragment

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.junedshaikh_project.databinding.FragmentThankyouBinding
import com.example.thefoodcoast.base.BaseFragment


class ThankyouFragment : BaseFragment<FragmentThankyouBinding>() {
    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentThankyouBinding.inflate(layoutInflater, container, false)

}