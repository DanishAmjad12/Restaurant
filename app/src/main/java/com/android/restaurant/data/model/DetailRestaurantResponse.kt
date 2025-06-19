package com.android.restaurant.data.model

import com.google.gson.annotations.SerializedName

data class DetailRestaurantResponse(
    @SerializedName("data")
    val data: RestaurantData

)