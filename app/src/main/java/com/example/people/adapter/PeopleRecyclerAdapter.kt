package com.example.people.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.people.databinding.RecyclerItemBinding

class PeopleRecyclerAdapter(context: Context): RecyclerView.Adapter<PeopleRecyclerAdapter.MViewHolder>() {
    private val binding by lazy {
        RecyclerItemBinding.inflate(LayoutInflater.from(context))
    }
    inner class MViewHolder :RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MViewHolder {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: MViewHolder, position: Int) {
        TODO("Not yet implemented")
    }
}