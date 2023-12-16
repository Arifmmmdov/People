package com.example.people.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.people.R
import com.example.people.databinding.RecyclerItemBinding
import com.example.people.db.DBCountry
import com.example.people.db.DBPerson
import com.example.people.extensions.getPeopleList

class PeopleRecyclerAdapter(private val context: Context, private val countryList: List<DBCountry>) :
    RecyclerView.Adapter<PeopleRecyclerAdapter.MViewHolder>() {

    var people: List<DBPerson> = countryList.getPeopleList()

    inner class MViewHolder(private val binding: RecyclerItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(person: DBPerson) {
            binding.name.text =
                context.getString(R.string.name_surname, person.name, person.surname)
        }

    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return MViewHolder(RecyclerItemBinding.inflate(layoutInflater, parent, false))
    }

    override fun getItemCount(): Int = people.size

    override fun onBindViewHolder(holder: MViewHolder, position: Int) {
        holder.bind(people[position])
    }

    fun filterList(isCountry: Boolean, selectedItems: List<String>) {
        people = selectedItems?.getPeopleList()!!
        this.notifyDataSetChanged()
    }

    fun filterList(filteredList: List<DBCountry>) {
        people = filteredList?.getPeopleList()!!
        this.notifyDataSetChanged()
    }
}