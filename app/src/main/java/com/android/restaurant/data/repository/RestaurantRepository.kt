package com.android.restaurant.data.repository

import com.android.restaurant.data.network.APIService
import javax.inject.Inject

class RestaurantRepository @Inject constructor(
    private val api: APIService
) {
    suspend fun getRestaurants(region:String) = api.getRestaurants(region)
    suspend fun getRestaurantDetail(id: String) = api.getRestaurantDetail(id)
}
