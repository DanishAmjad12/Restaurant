package com.android.restaurant.data.network

import com.android.restaurant.data.model.DetailRestaurantResponse
import com.android.restaurant.data.model.Restaurant
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface APIService
{
    @GET("consumer/v2/restaurants")
    suspend fun getRestaurants(@Query("region_id") regionId: String,@Query("q") search: String?=""): Restaurant

    @GET("consumer/v2/restaurants/{id}")
    suspend fun getRestaurantDetail(@Path("id") id: String): DetailRestaurantResponse

}