package com.example.mywhether.presentation.screens.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mywhether.data.Either
import com.example.mywhether.data.remote.models.WhetherModel
import com.example.mywhether.data.repositories.CityRepository
import com.example.mywhether.data.repositories.ForecastWhetherRepository
import com.example.mywhether.presentation.states.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val forecastWhetherRepository: ForecastWhetherRepository,
    private val cityRepository: CityRepository
) :
    ViewModel() {

    var currentWhetherListState = MutableStateFlow<UiState<MutableList<WhetherModel>>>(UiState.Loading())

    init {
        getCityList()
        viewModelScope.launch {
        }
    }

    fun setCity(city: String) {
        viewModelScope.launch {
            getCurrentWhether(city, onSuccess = {
                viewModelScope.launch {
                    cityRepository.setCity(it.location?.region!!)
                }
            }, onError = {
                Log.e("errorNotFound", "setCity: not found $it")
            })
        }
    }

    fun deleteCity(city: String) {
        Log.e("cityList", "deleteCity: $city")
        viewModelScope.launch {
            cityRepository.deleteCity(city)
        }
    }

    private fun getCityList() {
        viewModelScope.launch {
            cityRepository.getCityList().collect {
                it.collect { cityList ->
                    viewModelScope.launch {
                        val secondTestList = mutableListOf<WhetherModel>()
                        for (i in cityList) {
                            getCurrentWhether(i.city, onSuccess = { rootDto ->
                                secondTestList.add(rootDto)
                            }, onError = {

                            })
                        }
                        currentWhetherListState.value = UiState.Success(secondTestList)
                    }
                }
            }
        }
    }

    private suspend fun getCurrentWhether(
        city: String,
        onSuccess: (data: WhetherModel) -> Unit,
        onError: (code: Int) -> Unit
    ) {
        forecastWhetherRepository.getCurrentWhether(city).collect {
            when (it) {
                is Either.onError -> {
                    onError(it.error)
                    currentWhetherListState.value = UiState.Error(it.error)
                }

                is Either.onSuccess -> {
                    onSuccess(it.data)
                }
            }
        }
    }
}
