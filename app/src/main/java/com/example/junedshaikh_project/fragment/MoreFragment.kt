package com.example.junedshaikh_project.fragment

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import com.example.junedshaikh_project.activity.LoginActivity
import com.example.junedshaikh_project.databinding.FragmentMoreBinding
import com.example.thefoodcoast.base.BaseFragment
import com.google.firebase.auth.FirebaseAuth

class MoreFragment : BaseFragment<FragmentMoreBinding>() {

    private lateinit var auth: FirebaseAuth

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentMoreBinding.inflate(inflater, container, false)


    override fun initialSetUp() {
        super.initialSetUp()
        auth = FirebaseAuth.getInstance()

        var currentUser = auth.currentUser
        binding.nameTextView.text = currentUser?.displayName
        binding.emailTextView.text = currentUser?.email
        binding.passwordTextView.text = "********"

        binding.logoutButton.setOnClickListener {
            auth.signOut()
            startActivity(Intent(activity, LoginActivity::class.java))
            activity?.finish()
        }
    }
}