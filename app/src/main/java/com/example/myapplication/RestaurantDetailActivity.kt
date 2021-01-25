package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.myapplication.api.RetrofitClient
import com.example.myapplication.model.ResultItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RestaurantDetailActivity : AppCompatActivity() {
    private lateinit var details: ResultItem

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_restaurant_detail)
        val id = intent.getLongExtra(RESTAURANT_ID, 0L)
        val call = RetrofitClient.apiService.getRestaurantDetails(id)
        call.enqueue(object : Callback<ResultItem> {
            override fun onResponse(
                call: Call<ResultItem>, response: Response<ResultItem>
            ) {
                response.body()?.let {item ->
                    details = item
                    addDetails(item)
                }
            }

            override fun onFailure(call: Call<ResultItem>, t: Throwable) {
                Log.e(TAG, "FAILED!" + t.message)
            }
        })
        //TODO adding addtional details to view from call response
    }

    private fun addDetails(item: ResultItem) {
        findViewById<ImageView>(R.id.iv_cover_image)?.let {coverImage ->
            Glide.with(this).load(item.cover_img_url).into(coverImage)
        }
        findViewById<TextView>(R.id.tv_name)?.let {name ->
            name.text = item.name
        }
    }

    companion object {
        const val RESTAURANT_ID = "restaurant_id"
        const val TAG = "RestaurantDetail"
    }
}