package com.veriaw.dolanyogya.model

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.veriaw.dolanyogya.data.PlaceRepository
import com.veriaw.dolanyogya.data.entity.PlaceEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class PlaceViewModel(application: Application): ViewModel() {
    private val placeRepository: PlaceRepository = PlaceRepository(application)

    fun getAllPlaces(): LiveData<List<PlaceEntity>> = placeRepository.getALlPlaces().asLiveData()
    fun getCurrentPlaces(id: Int): LiveData<PlaceEntity> = placeRepository.getCurrentPlaces(id).asLiveData()
    fun getSearchQuery(searchQuery: String): LiveData<List<PlaceEntity>> = placeRepository.getSearchQuery(searchQuery).asLiveData()

    fun insertPlace(place: PlaceEntity){
        viewModelScope.launch {
            placeRepository.insertPlace(place)
        }
    }

    fun deletePlace(place: PlaceEntity){
        viewModelScope.launch {
            placeRepository.deletePlace(place)
        }
    }

    fun updatePlace(place: PlaceEntity){
        viewModelScope.launch {
            placeRepository.updatePlace(place)
        }
    }
}