package com.example.people.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.people.R
import com.example.people.databinding.RecyclerItemBinding
import com.example.people.model.Country
import com.example.people.model.Person

class PeopleRecyclerAdapter(private val context: Context, private val countryList: List<Country>) :
    RecyclerView.Adapter<PeopleRecyclerAdapter.MViewHolder>() {
    private lateinit var binding: RecyclerItemBinding

    var people: List<Person> = countryList.flatMap {
        it.cities.flatMap {
            it.people
        }
    }

    inner class MViewHolder(binding: RecyclerItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(person: Person) {
            binding.name.text =
                context.getString(R.string.name_surname, person.name, person.surname)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        binding = RecyclerItemBinding.inflate(layoutInflater, parent, false)
        return MViewHolder(binding)
    }

    override fun getItemCount(): Int = people.size

    override fun onBindViewHolder(holder: MViewHolder, position: Int) {
        holder.bind(people[position])
    }
}