package com.example.demexam.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.demexam.Global
import com.example.demexam.R
import com.example.demexam.databinding.RecyclerCityBinding
import com.example.demexam.model.CityModel

class CityAdapter (val listner: Listner) : RecyclerView.Adapter<CityAdapter.CityViewHolder>() {

    class CityViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val binding = RecyclerCityBinding.bind(itemView)
        fun bind(cityModel: CityModel, listner: Listner) = with(binding){
            cityTextView.text = cityModel.city

            itemView.setOnClickListener {
                listner.onClickItem(cityModel)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityViewHolder {
        return CityViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.recycler_city, parent, false))
    }

    override fun onBindViewHolder(holder: CityViewHolder, position: Int) {
        holder.bind(Global.citys[position], listner)
    }

    override fun getItemCount() = Global.citys.size


    interface Listner {
        fun onClickItem(cityModel: CityModel)
    }
}