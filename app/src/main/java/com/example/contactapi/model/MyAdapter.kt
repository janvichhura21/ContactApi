package com.example.contactapi.model

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.contactapi.model.Listeners
import com.example.contactapi.databinding.EachRowBinding

class MyAdapter constructor(val listeners: Listeners):ListAdapter<Phone,MyAdapter.MyViewHolder>(diff) {
    inner class MyViewHolder(val binding: EachRowBinding):RecyclerView.ViewHolder(binding.root) {
        fun bind(data: Phone){
            binding.apply {
                phoneNo.text=data.phoneNo.toString()
                name.text=data.name
                delete.setOnClickListener {
                    listeners.onClickDelete(adapterPosition,data.userId)
                }
                root.setOnClickListener{
                    listeners.onClickUpdate(adapterPosition,data.userId,data.name,data.phoneNo)
                }

            }
        }
    }
    object diff:DiffUtil.ItemCallback<Phone>(){
        override fun areItemsTheSame(oldItem: Phone, newItem: Phone): Boolean {
            return oldItem.userId==newItem.userId
        }

        override fun areContentsTheSame(oldItem: Phone, newItem: Phone): Boolean {
           return oldItem.name==newItem.name
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(EachRowBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val phone = getItem(position)
        if(phone != null){
            holder.bind(phone)
        }
    }
}