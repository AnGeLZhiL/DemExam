package com.example.demexam

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.demexam.databinding.ActivitySignInBinding
import okhttp3.*
import java.io.IOException

class SignIn : AppCompatActivity() {
    private lateinit var binding: ActivitySignInBinding
    private val emailRegex = "[a-z0-9]+@[a-z0-9]+\\.+[a-z]{2,3}"
    private val client = OkHttpClient()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.signUpOnClick.setOnClickListener {
            startActivity(Intent(this, SignUp::class.java))
        }
        binding.signInOnClick.setOnClickListener {
            if (binding.emailEditText.text.isNotEmpty() and binding.passwordEditText.text.isNotEmpty()){
                if (binding.emailEditText.text.toString().trim().matches(emailRegex.toRegex())){
                    val formBody = FormBody.Builder()
                        .add("email", binding.emailEditText.text.toString())
                        .add("password", binding.passwordEditText.text.toString())
                        .build()
                    val request = Request.Builder()
                        .url("http://wsk2019.mad.hakta.pro/api/user/login")
                        .post(formBody)
                        .build()
                    client.newCall(request).enqueue(object : Callback{
                        override fun onFailure(call: Call, e: IOException) {
                            println("Noooo")
                        }

                        override fun onResponse(call: Call, response: Response) {
                            println("-------------   " + response.code())
                        }

                    })
                }
                else binding.emailEditText.error = "Некорректное заполнение"
            }
            else {
                if (binding.emailEditText.text.isEmpty()) binding.emailEditText.error = "Пустое поле"
                if (binding.passwordEditText.text.isEmpty()) binding.passwordEditText.error = "Пустое поле"
            }
        }
    }
}