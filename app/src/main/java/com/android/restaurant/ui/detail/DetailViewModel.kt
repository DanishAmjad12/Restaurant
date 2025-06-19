package com.android.restaurant.ui.detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.restaurant.data.model.DetailRestaurantResponse
import com.android.restaurant.data.model.Restaurant
import com.android.restaurant.data.model.RestaurantData
import com.android.restaurant.data.repository.RestaurantRepository
import com.android.restaurant.util.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val repository: RestaurantRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _uiState = MutableLiveData<UiState<DetailRestaurantResponse>>()
    val uiState: LiveData<UiState<DetailRestaurantResponse>> = _uiState

    private val _restaurantData = MutableLiveData<RestaurantData?>()
    val restaurantData: LiveData<RestaurantData?> = _restaurantData

    init {
        val restaurant = savedStateHandle.get<RestaurantData>("restaurant")
        _restaurantData.value = restaurant
        restaurant?.id?.let { getRestaurantDetail(it) }
    }

    private fun getRestaurantDetail(id: String) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            try {
                val data = repository.getRestaurantDetail(id)
                _uiState.value = UiState.Success(data)
            } catch (e: Exception) {
                _uiState.value = UiState.Error(e.message ?: "Unknown error")
            }
        }
    }
}
