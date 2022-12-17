package com.example.demexam

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.demexam.databinding.ActivitySignInBinding
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException

class SignIn : AppCompatActivity() {
    private lateinit var binding: ActivitySignInBinding
    private val emailRegex = "[a-z0-9]+@[a-z0-9]+\\.+[a-z]{2,3}"
    private val client = OkHttpClient()
    private lateinit var alertDialog: AlertDialog.Builder

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        alertDialog = AlertDialog.Builder(this)

        binding.signUpOnClick.setOnClickListener {
            startActivity(Intent(this, SignUp::class.java))
        }
        binding.signInOnClick.setOnClickListener {
            if (binding.emailEditText.text.isNotEmpty() and binding.passwordEditText.text.isNotEmpty()){
                if (binding.emailEditText.text.toString().trim().matches(emailRegex.toRegex())){
                    val requestBody = RequestBody.create(
                        "application/json".toMediaTypeOrNull(),
                        JSONObject()
                            .put("email", binding.emailEditText.text.toString())
                            .put("password", binding.passwordEditText.text.toString())
                            .toString()
                    )

                    """
                        request = Request.Builder()
                            .url(*ссылка*)
                            .addHeader(*заголовок*, *значение*) // необязательно
                            .post(*тело запроса*) // если POST запрос
                            .build()
                    """.trimIndent()
                    val request = Request.Builder()
                        .url("${Global.base_url}/user/login")
                        .post(requestBody)
                        .build()
                    client.newCall(request).enqueue(object : Callback {
                        override fun onFailure(call: Call, e: IOException) {
                            this@SignIn.runOnUiThread(java.lang.Runnable {
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
                                this@SignIn.runOnUiThread(java.lang.Runnable {
                                    startActivity(Intent(this@SignIn, MainActivity::class.java))
                                })
                                Global.token = json.getString("token")
                            }
                            if (response.code == 469){
                                this@SignIn.runOnUiThread(java.lang.Runnable {
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
                else binding.emailEditText.error = "Некорректное заполнение"
            }
            else {
                if (binding.emailEditText.text.isEmpty()) binding.emailEditText.error = "Пустое поле"
                if (binding.passwordEditText.text.isEmpty()) binding.passwordEditText.error = "Пустое поле"
            }
        }
    }
}