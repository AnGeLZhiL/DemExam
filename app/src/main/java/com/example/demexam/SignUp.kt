package com.example.demexam

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.demexam.databinding.ActivitySignUpBinding
import okhttp3.OkHttpClient

class SignUp : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding
    private val emailRegex = "[a-z0-9]+@[a-z0-9]+\\.+[a-z]{2,3}"
    private val client = OkHttpClient()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.signInOnClick.setOnClickListener {
            startActivity(Intent(this, SignIn::class.java))
        }

        binding.signUpOnClick.setOnClickListener {
            if (binding.emailEditText.text.isNotEmpty() and binding.passwordEditText.text.isNotEmpty() and binding.nameEditText.text.isNotEmpty()){
                if (binding.emailEditText.text.toString().trim().matches(emailRegex.toRegex())){

                }
                else binding.emailEditText.error = "Некорректное заполнение"
            }
            else {
                if (binding.emailEditText.text.isEmpty()) binding.emailEditText.error = "Пустое поле"
                if (binding.passwordEditText.text.isEmpty()) binding.passwordEditText.error = "Пустое поле"
                if (binding.nameEditText.text.isEmpty()) binding.nameEditText.error = "Пустое поле"
            }
        }
    }
}