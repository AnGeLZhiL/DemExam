package com.example.demexam

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.demexam.adapter.CityAdapter
import com.example.demexam.databinding.FragmentListenBinding
import com.example.demexam.model.CityModel
import okhttp3.*
import okio.IOException
import org.json.JSONArray

class ListenFragment : Fragment(), CityAdapter.Listner {
    private lateinit var binding: FragmentListenBinding
    private val adapter = CityAdapter(this)
    private val client = OkHttpClient()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentListenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val request = Request.Builder()
            .url("${Global.base_url}/cities")
            .build()
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {

            }

            override fun onResponse(call: Call, response: Response) {
                if (response.code == 200){
                    val r = JSONArray(response.body.string())
                    for (i in 0 until r.length()) {
                        Global.citys.add(CityModel(r[i].toString()))
                    }
                    Handler(Looper.getMainLooper()).post {
                        binding.citysRecycler.adapter = adapter
                    }
                }
                else println("Ошибочка вышла")
            }
        })
    }

    override fun onClickItem(cityModel: CityModel) {
        findNavController().navigate(R.id.action_listenFragment_to_testFragment2)
        Global.selectCity = cityModel.city
    }
}