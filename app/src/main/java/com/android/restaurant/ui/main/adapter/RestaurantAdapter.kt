package com.android.restaurant.ui.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.RoundedCornersTransformation
import com.android.restaurant.R
import com.android.restaurant.databinding.ItemRestaurantBinding
import com.android.restaurant.data.model.RestaurantData

class RestaurantAdapter(
    private val restaurants: ArrayList<RestaurantData>,
    private val onItemClick: (RestaurantData) -> Unit
) : RecyclerView.Adapter<RestaurantAdapter.RestaurantViewHolder>() {

    inner class RestaurantViewHolder(val binding: ItemRestaurantBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RestaurantViewHolder {
        val binding = ItemRestaurantBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RestaurantViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RestaurantViewHolder, position: Int) {
        val restaurant = restaurants[position]
        holder.binding.tvRating.text=restaurant.attributes.ratingsAverage
        holder.binding.tvRestaurantName.text = restaurant.attributes.name
        holder.binding.tvRestaurantAddress.text=restaurant.attributes.addressLine1
        holder.binding.tvCuisine.text = restaurant.attributes.cuisine
        holder.binding.ivRestaurantImage.load(restaurant.attributes.imageUrl) {
            crossfade(true)
            placeholder(R.drawable.bg_image_rounded)
            error(R.drawable.bg_image_rounded)
            transformations(RoundedCornersTransformation(35f))
        }
        holder.itemView.setOnClickListener {
            onItemClick(restaurant)
        }
    }

    override fun getItemCount(): Int = restaurants.size
}
