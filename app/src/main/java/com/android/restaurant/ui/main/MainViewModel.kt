package com.android.restaurant.ui.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.restaurant.data.model.Restaurant
import com.android.restaurant.data.model.RestaurantData
import com.android.restaurant.data.repository.RestaurantRepository
import com.android.restaurant.util.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: RestaurantRepository
) : ViewModel() {


    private val _uiState = MutableLiveData<UiState<ArrayList<RestaurantData>>>()
    val uiState: LiveData<UiState<ArrayList<RestaurantData>>> = _uiState

    init {
        getRestaurants("3906535a-d96c-47cf-99b0-009fc9e038e0","")
    }

    fun getRestaurants(region:String,query:String?="") {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            try {
                val data = repository.getRestaurants(region,query)
                Log.e("ViewModel", "Success: ${data}")
                _uiState.value = UiState.Success(data.data)
            } catch (e: Exception) {
                Log.e("ViewModel", "Error: ${e.localizedMessage}")
                _uiState.value = UiState.Error(e.message ?: "Unknown error")
            }
        }
    }
}