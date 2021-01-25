package com.example.myapplication.api
import com.example.myapplication.model.ResultItem
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface APIService {
    @GET("/v2/restaurant/")
    fun searchRestaurants(@Query("lat") latitude: Double, @Query("lng") longitude: Double): Call<List<ResultItem>>

    @GET("/v2/restaurant/{id}")
    fun getRestaurantDetails(@Path("id") id: Long): Call<ResultItem>
}