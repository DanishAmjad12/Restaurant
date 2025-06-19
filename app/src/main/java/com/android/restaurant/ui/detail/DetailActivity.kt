package com.android.restaurant.ui.detail

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import coil.load
import com.android.restaurant.R
import com.android.restaurant.data.model.DetailRestaurantResponse
import com.android.restaurant.databinding.ActivityDetailBinding
import com.android.restaurant.ui.detail.adapter.LabelsAdapter
import com.android.restaurant.util.UiState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailActivity : AppCompatActivity() {

    private val viewModel: DetailViewModel by viewModels()
    private lateinit var binding: ActivityDetailBinding
    private lateinit var adapter: LabelsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupToolbar()
        observeRestaurant()
        observeUiState()
    }

    private fun setupToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            title = "Back"
        }
    }

    private fun observeRestaurant() {
        viewModel.restaurantData.observe(this) { restaurant ->
            restaurant?.let {
                binding.imageHeader.load(it.attributes.imageUrl) {
                    crossfade(true)
                    placeholder(R.drawable.bg_image_rounded)
                    error(R.drawable.bg_image_rounded)
                }
                binding.tvAddress.text = it.attributes.addressLine1
                binding.textTitle.text = it.attributes.name
                binding.tvRating.text = it.attributes.ratingsAverage
                binding.tvCuisine.text = it.attributes.cuisine
            }
        }
    }

    private fun observeUiState() {
        viewModel.uiState.observe(this) { state ->
            when (state) {
                is UiState.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
                is UiState.Success -> {
                    binding.progressBar.visibility = View.GONE
                    updateUI(state.data)
                }
                is UiState.Error -> {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(this, state.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun updateUI(restaurant: DetailRestaurantResponse) {
        binding.textDescription.text = restaurant.data.attributes.description
        if (restaurant.data.attributes.labels.isNotEmpty()) {
            setupRecyclerView(restaurant.data.attributes.labels)
        } else {
            binding.recycler.visibility = View.GONE
        }
    }

    private fun setupRecyclerView(labels: List<String>) {
        adapter = LabelsAdapter(labels)
        binding.recycler.layoutManager = GridLayoutManager(this, 3)
        binding.recycler.adapter = adapter
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (item.itemId == android.R.id.home) {
            onBackPressedDispatcher.onBackPressed()
            true
        } else {
            super.onOptionsItemSelected(item)
        }
    }
}
