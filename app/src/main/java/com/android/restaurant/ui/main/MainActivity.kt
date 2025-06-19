package com.android.restaurant.ui.main

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.restaurant.databinding.ActivityMainBinding
import com.android.restaurant.ui.detail.DetailActivity
import com.android.restaurant.ui.main.adapter.RestaurantAdapter
import com.android.restaurant.util.NetworkUtils
import com.android.restaurant.util.UiState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity: AppCompatActivity()
{
    private val viewModel: MainViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: RestaurantAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        setupRecyclerView()
        searchRestaurant()

        callAPI()

        viewModel.uiState.observe(this) { state ->
            when (state) {
                is UiState.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                    binding.recycler.visibility = View.GONE
                }
                is UiState.Success -> {
                    binding.progressBar.visibility = View.GONE
                    binding.recycler.visibility = View.VISIBLE
                    adapter = RestaurantAdapter(state.data) { restaurant ->
                        Log.e("MainActivity", "onCreate: $restaurant")
                        val intent= Intent(this, DetailActivity::class.java)
                        val bundle = Bundle()
                        bundle.putParcelable("restaurant", restaurant)
                        intent.putExtras(bundle)
                        startActivity(intent)
                    }
                    binding.recycler.adapter = adapter
                }
                is UiState.Error -> {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(this, state.message, Toast.LENGTH_SHORT).show()
                }
            }
        }

    }

    private fun callAPI(query:String?="") {
        if(NetworkUtils.isNetworkAvailable(this))
            viewModel.getRestaurants("3906535a-d96c-47cf-99b0-009fc9e038e0",query)
        else
            Toast.makeText(this, "No internet connection", Toast.LENGTH_SHORT).show()

    }

    private fun setupRecyclerView() {
        binding.recycler.layoutManager = LinearLayoutManager(this)

    }

    private fun searchRestaurant()
    {
        lifecycleScope.launch {
            binding.etSearch.textChanges()
                .debounce(500) // waits 500ms after user stops typing
                .filter { it.isNotEmpty() } // optional: skip empty input
                .distinctUntilChanged()
                .collect { query ->
                    callAPI(query) // your API function
                }
        }
    }

    fun EditText.textChanges(): Flow<String> = callbackFlow {
        val watcher = object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                trySend(s.toString())
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        }
        addTextChangedListener(watcher)
        awaitClose { removeTextChangedListener(watcher) }
    }

}