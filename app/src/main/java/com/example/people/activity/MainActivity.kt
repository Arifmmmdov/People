package com.example.people.activity

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.people.adapter.PeopleRecyclerAdapter
import com.example.people.databinding.ActivityMainBinding
import com.example.people.db.DBCountry
import com.example.people.viewmodel.PeopleViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    @Inject
    lateinit var viewModel: PeopleViewModel

    private var adapter: PeopleRecyclerAdapter? = null

    @Inject
    lateinit var name: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        setUpViewModel()
        setListeners()
    }

    private fun setListeners() {
        binding.refreshLayout.setOnRefreshListener {
            viewModel.fetchData(true)
        }
    }

    private fun setUpViewModel() {
        viewModel.fetchData(false)
        viewModel.data.observe(this) { data ->
            if (adapter == null) {
                setUpRecycler(data)
            } else {
                Log.d("MyTagHere", "setUpViewModel: Data added or filtered!")
                adapter!!.filterList(data)
                Toast.makeText(this, "Data added or filtered!", Toast.LENGTH_LONG).show()
            }
            binding.refreshLayout.isRefreshing = false
        }

    }

    private fun setUpRecycler(countries: List<DBCountry>?) {
        binding.recyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        adapter = PeopleRecyclerAdapter(this, countries!!)
        binding.recyclerView.adapter = adapter
    }
}