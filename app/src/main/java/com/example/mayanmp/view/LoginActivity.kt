package com.example.mayanmp.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.mayanmp.model.SharePreferences
import com.example.mayanmp.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var sharePreferences: SharePreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_login)
        sharePreferences = SharePreferences(this)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        val view = binding.root
        if (sharePreferences.isLogin()) {
            // Sudah login, langsung ke MainActivity
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        } else {
            // Belum login, tampilkan LoginFragment
            setContentView(view)
        }
    }
}