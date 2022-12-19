package com.example.demexam

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.demexam.adapter.DepAdatper
import com.example.demexam.databinding.FragmentMainBinding
import com.example.demexam.model.DepModel
import okhttp3.*
import okio.IOException
import org.json.JSONObject

class MainFragment : Fragment(), DepAdatper.Listner {
    private lateinit var binding: FragmentMainBinding
    private val adapter = DepAdatper(this)
    private val client = OkHttpClient()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val request = Request.Builder()
            .url("http://mad2019.hakta.pro/api/department")
            .build()
        client.newCall(request).enqueue(object : Callback{
            override fun onFailure(call: Call, e: IOException) {
                TODO("Not yet implemented")
            }

            override fun onResponse(call: Call, response: Response) {
                if (response.code == 200){
                    val jsonArray = JSONObject(response.body.string()).getJSONArray("data")
                    for (i in 0 until jsonArray.length()){
                        Global.depList.add(
                            DepModel(
                                id = jsonArray.getJSONObject(i).getString("id"),
                                nickname = jsonArray.getJSONObject(i).getString("name"),
                                adress = jsonArray.getJSONObject(i).getString("address"),
                                description = jsonArray.getJSONObject(i).getString("description")
                            )
                        )
                    }
                    Handler(Looper.getMainLooper()).post {
                        binding.recyclerItem.adapter = adapter
                    }

                }
            }

        })
    }

    override fun onClickDep(depModel: DepModel) {
        findNavController().navigate(R.id.action_mainFragment_to_testFragment)
        Global.name = depModel.nickname
    }
}