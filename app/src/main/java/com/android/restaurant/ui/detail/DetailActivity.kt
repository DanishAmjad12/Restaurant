package com.android.restaurant.ui.detail

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import coil.load
import com.android.restaurant.R
import com.android.restaurant.data.model.DetailRestaurantResponse
import com.android.restaurant.data.model.Restaurant
import com.android.restaurant.data.model.RestaurantData
import com.android.restaurant.databinding.ActivityDetailBinding
import com.android.restaurant.ui.detail.adapter.LabelsAdapter
import com.android.restaurant.util.NetworkUtils
import com.android.restaurant.util.UiState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailActivity: AppCompatActivity()
{
    private val viewModel: DetailViewModel by viewModels()
    private lateinit var binding: ActivityDetailBinding
    private lateinit var adapter: LabelsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowTitleEnabled(true)
            title = "Back"
        }

        if(intent.extras!=null)
        {
            val restaurant = intent.getParcelableExtra<RestaurantData>("restaurant")
            val id=restaurant?.id ?: ""
            binding.imageHeader.load(restaurant?.attributes?.imageUrl) {
                crossfade(true)
                placeholder(R.drawable.bg_image_rounded)
                error(R.drawable.bg_image_rounded)
            }
            binding.tvAddress.text=restaurant?.attributes?.addressLine1
            binding.textTitle.text=restaurant?.attributes?.name
            binding.tvRating.text=restaurant?.attributes?.ratingsAverage
            binding.tvCuisine.text=restaurant?.attributes?.cuisine
            callAPI(id)
        }

        viewModel.uiState.observe(this) { state ->
            when (state) {
                is UiState.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
                is UiState.Success -> {
                    binding.progressBar.visibility = View.GONE
                    Log.e("DetailActivity", "onCreate: ${state.data}")
                    updateUI(state.data)

                }
                is UiState.Error -> {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(this, state.message, Toast.LENGTH_SHORT).show()
                }
            }
        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressedDispatcher.onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun callAPI(id:String)
    {
        if(NetworkUtils.isNetworkAvailable(this))
            viewModel.getRestaurantDetail(id)
        else
            Toast.makeText(this, "No internet connection", Toast.LENGTH_SHORT).show()
    }
    private fun updateUI(restaurant: DetailRestaurantResponse)
    {
        binding.textDescription.text=restaurant.data.attributes.description
        if(restaurant.data.attributes.labels.isNotEmpty())
            setupRecyclerView(restaurant.data.attributes.labels)
    }

    private fun setupRecyclerView(labels:List<String>) {
        binding.recycler.layoutManager = GridLayoutManager(this,3)
        adapter = LabelsAdapter(labels)
        binding.recycler.adapter = adapter
    }
}