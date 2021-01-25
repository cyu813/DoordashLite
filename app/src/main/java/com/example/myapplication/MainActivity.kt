package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.adapter.ResultsAdapter
import com.example.myapplication.api.APIService
import com.example.myapplication.api.RetrofitClient
import com.example.myapplication.model.ResultItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private lateinit var apiService: APIService
    private lateinit var recyclerView: RecyclerView
    private var results: MutableList<ResultItem> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recyclerView = findViewById(R.id.rv_results)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = ResultsAdapter(this, results)
        val lat = 37.422740 //default
        val long = -122.139956 //default
        val call = RetrofitClient.apiService.searchRestaurants(lat, long)
        call.enqueue(object : Callback<List<ResultItem>> {
            override fun onResponse(
                call: Call<List<ResultItem>>, response: Response<List<ResultItem>>) {
                response.body()?.let {list ->
                    results.addAll(list)
                    recyclerView.adapter?.notifyDataSetChanged()
                }
            }

            override fun onFailure(call: Call<List<ResultItem>>, t: Throwable) {
                Log.e(TAG, "FAILED!" + t.message)
            }
        })

    }

    companion object {
        const val TAG = "MainActivity"
    }
}