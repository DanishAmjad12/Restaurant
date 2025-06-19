package com.android.restaurant.ui.detail.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.RoundedCornersTransformation
import com.android.restaurant.R
import com.android.restaurant.databinding.ItemRestaurantBinding
import com.android.restaurant.data.model.RestaurantData
import com.android.restaurant.databinding.ItemLabelsBinding

class LabelsAdapter(
    private val labels: List<String>
) : RecyclerView.Adapter<LabelsAdapter.RestaurantViewHolder>() {

    inner class RestaurantViewHolder(val binding: ItemLabelsBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RestaurantViewHolder {
        val binding = ItemLabelsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RestaurantViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RestaurantViewHolder, position: Int) {
        val labels = labels[position]
        holder.binding.tvLabels.text=labels
    }

    override fun getItemCount(): Int = labels.size
}
