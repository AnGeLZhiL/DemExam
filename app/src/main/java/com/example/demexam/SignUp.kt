package com.example.demexam

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.demexam.databinding.ActivitySignUpBinding
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import org.json.JSONObject
import java.io.IOException

class SignUp : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding
    private val emailRegex = "[a-z0-9]+@[a-z0-9]+\\.+[a-z]{2,3}"
    private var client = OkHttpClient()
    private lateinit var alertDialog: AlertDialog.Builder

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        alertDialog = AlertDialog.Builder(this)

        binding.signInOnClick.setOnClickListener {
            startActivity(Intent(this, SignIn::class.java))
        }

        binding.signUpOnClick.setOnClickListener {
            if (binding.emailEditText.text.isNotEmpty() and binding.passwordEditText.text.isNotEmpty() and binding.nameEditText.text.isNotEmpty()){
                if (binding.emailEditText.text.toString().trim().matches(emailRegex.toRegex())){
                    val requestBody = RequestBody.create(
                        "application/json".toMediaTypeOrNull(),
                        JSONObject()
                            .put("email", binding.emailEditText.text.toString())
                            .put("nickName", binding.nameEditText.text.toString())
                            .put("password", binding.passwordEditText.text.toString())
                            .put("phone", "89117263524")
                            .toString()
                    )
                    val request = Request.Builder()
                        .url("${Global.base_url}/users")
                        .post(requestBody)
                        .build()
                    client.newCall(request).enqueue(object : Callback {
                        override fun onFailure(call: Call, e: IOException) {
                            this@SignUp.runOnUiThread(java.lang.Runnable{
                                alertDialog
                                    .setTitle("Ошибка")
                                    .setMessage("Произошла ошибка при соединении с сервером")
                                    .setCancelable(true)
                                    .setPositiveButton("Yes"){dialog, it ->
                                        dialog.cancel()
                                    }
                                    .show()
                            })
                        }

                        override fun onResponse(call: Call, response: Response) {
                            if (response.code == 200){
                                SignIn(binding.emailEditText.text.toString(), binding.passwordEditText.text.toString())
                            }
                            if (response.code == 465){
                                this@SignUp.runOnUiThread(java.lang.Runnable {
                                    alertDialog
                                        .setTitle("Ошибка")
                                        .setMessage("Пользователь с таким логином уже зарегестрирован")
                                        .setCancelable(true)
                                        .setPositiveButton("Yes"){dialog, it ->
                                            dialog.cancel()
                                        }
                                        .show()
                                })
                            }
                        }

                    })
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

    fun SignIn(email: String, password: String){
        val requestBody = RequestBody.create(
            "application/json".toMediaTypeOrNull(),
            JSONObject()
                .put("email", email)
                .put("password", password)
                .toString()
        )
        val request = Request.Builder()
            .url("${Global.base_url}/user/login")
            .post(requestBody)
            .build()
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                this@SignUp.runOnUiThread(java.lang.Runnable {
                    alertDialog
                        .setTitle("Ошибка")
                        .setMessage("Произошла ошибка при связи с сервером")
                        .setCancelable(true)
                        .setPositiveButton("Ок") { dialog, it ->
                            dialog.cancel()
                        }
                })
            }
            override fun onResponse(call: Call, response: Response) {
                if (response.code == 200){
                    val json = JSONObject(response.body.string())
                    this@SignUp.runOnUiThread(java.lang.Runnable {
                        startActivity(Intent(this@SignUp, MainActivity::class.java))
                        finish()
                    })
                    Global.token = json.getString("token")
                }
                if (response.code == 469){
                    this@SignUp.runOnUiThread(java.lang.Runnable {
                        alertDialog
                            .setTitle("Ошибка")
                            .setMessage("Пользователь с таким логином не найден")
                            .setCancelable(true)
                            .setPositiveButton("Ok"){dialog, it ->
                                dialog.cancel()
                            }.show()
                    })
                }
            }

        })
    }
}