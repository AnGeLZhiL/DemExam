package com.example.demexam

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.demexam.databinding.FragmentProfileBinding
import com.squareup.picasso.Picasso
import okhttp3.*
import org.json.JSONObject
import java.io.IOException

class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding
    private val client = OkHttpClient()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val request = Request.Builder()
            .addHeader("Token", Global.token)
            .url("${Global.base_url}/user/profile")
            .build()
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                println(e.toString())
            }

            override fun onResponse(call: Call, response: Response) {
                val json = JSONObject(response.body.string())
                val jsonObject = json.getJSONObject("content")
                println(jsonObject)
                Global.user.id = jsonObject.getString("id")
                Global.user.email = jsonObject.getString("email")
                Global.user.firstname = jsonObject.getString("firstName")
                Global.user.lastName = jsonObject.getString("lastName")
                Global.user.nickName = jsonObject.getString("nickName")
                Global.user.image = jsonObject.getString("avatar")
                Global.user.city = jsonObject.getString("city")
                Handler(Looper.getMainLooper()).post {
                    binding.nickNameTextView.text = Global.user.nickName
                    binding.emailTextView.text = Global.user.email
                    binding.cityTextView.text = Global.user.city
                    Picasso.get().load(Global.user.image).into(binding.imageUser)
                }
            }
        })
    }
}