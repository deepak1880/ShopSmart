package com.example.junedshaikh_project.fragment

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.junedshaikh_project.databinding.FragmentMoreBinding
import com.example.thefoodcoast.base.BaseFragment

class MoreFragment : BaseFragment<FragmentMoreBinding>() {
    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentMoreBinding.inflate(inflater, container, false)

    override fun initialSetUp() {
        super.initialSetUp()
    }
}