package com.example.demexam.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.demexam.Global
import com.example.demexam.R
import com.example.demexam.databinding.RecyclerItemBinding
import com.example.demexam.model.DepModel

class DepAdatper(val listner: Listner) : RecyclerView.Adapter<DepAdatper.DepViewHolder>() {
    class DepViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val binding = RecyclerItemBinding.bind(itemView)
        fun bind(depModel: DepModel, listner: Listner) = with(binding){
            nickName.text = depModel.nickname
            adress.text = depModel.adress
            itemView.setOnClickListener {
                listner.onClickDep(depModel)
            }
        }
    }
    interface Listner {
        fun onClickDep (depModel: DepModel)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DepViewHolder {
        return DepViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.recycler_item, parent, false))
    }

    override fun onBindViewHolder(holder: DepViewHolder, position: Int) {
        holder.bind(Global.depList[position], listner)
    }

    override fun getItemCount() = Global.depList.size
}