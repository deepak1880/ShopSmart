package com.example.junedshaikh_project.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.junedshaikh_project.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initialSetup()
    }

    private fun initialSetup() {
        binding.forgotPasswordTextView.setOnClickListener {
            val iNext = Intent(this, ForgotActivity::class.java)
            startActivity(iNext)
        }
        binding.registerTextView.setOnClickListener {
            val iNext = Intent(this, RegistorActivity::class.java)
            startActivity(iNext)
        }
    }
}