package com.example.demexam

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.demexam.databinding.FragmentProfileBinding
import okhttp3.*
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
                println(response.body()?.string().toString())
            }
        })
    }
}