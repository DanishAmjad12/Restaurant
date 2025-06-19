package com.android.restaurant.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Restaurant(
    @SerializedName("data")
    val data: ArrayList<RestaurantData>,

    @SerializedName("meta")
    val meta: Meta,

    @SerializedName("links")
    val links: Links
) : Parcelable

@Parcelize
data class RestaurantData(
    @SerializedName("id")
    val id: String,

    @SerializedName("type")
    val type: String,

    @SerializedName("attributes")
    val attributes: RestaurantAttributes,

    @SerializedName("relationships")
    val relationships: Relationships
) : Parcelable

@Parcelize
data class RestaurantAttributes(
    @SerializedName("name")
    val name: String,

    @SerializedName("price_level")
    val priceLevel: Int,

    @SerializedName("phone")
    val phone: String? = null,

    @SerializedName("menu_url")
    val menuUrl: String? = null,

    @SerializedName("require_booking_preference_enabled")
    val requireBookingPreferenceEnabled: Boolean,

    @SerializedName("difficult")
    val difficult: Boolean,

    @SerializedName("cuisine")
    val cuisine: String,

    @SerializedName("image_url")
    val imageUrl: String,

    @SerializedName("latitude")
    val latitude: Double,

    @SerializedName("longitude")
    val longitude: Double,

    @SerializedName("address_line_1")
    val addressLine1: String,

    @SerializedName("description")
    val description: String? = null,

    @SerializedName("ratings_average")
    val ratingsAverage: String? = null,

    @SerializedName("ratings_count")
    val ratingsCount: Int? = null,

    @SerializedName("labels")
    val labels: List<String>
) : Parcelable

@Parcelize
data class Relationships(
    @SerializedName("region")
    val region: Region
) : Parcelable

@Parcelize
data class Region(
    @SerializedName("data")
    val data: RegionData
) : Parcelable

@Parcelize
data class RegionData(
    @SerializedName("id")
    val id: String,

    @SerializedName("type")
    val type: String
) : Parcelable

@Parcelize
data class Meta(
    @SerializedName("limit")
    val limit: Int,

    @SerializedName("total_pages")
    val totalPages: Int,

    @SerializedName("total_count")
    val totalCount: Int,

    @SerializedName("current_page")
    val currentPage: Int
) : Parcelable

@Parcelize
data class Links(
    @SerializedName("first")
    val first: String,

    @SerializedName("next")
    val next: String? = null,

    @SerializedName("prev")
    val prev: String? = null,

    @SerializedName("last")
    val last: String
) : Parcelable