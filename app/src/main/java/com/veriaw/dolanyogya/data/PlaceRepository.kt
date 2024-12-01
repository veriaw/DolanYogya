package com.veriaw.dolanyogya.data

import android.app.Application
import androidx.room.Query
import com.veriaw.dolanyogya.data.entity.PlaceEntity
import com.veriaw.dolanyogya.data.local.PlaceDao
import com.veriaw.kriptografiapp.data.entity.UserEntity
import com.veriaw.kriptografiapp.data.local.AppDatabase
import com.veriaw.kriptografiapp.data.local.UserDao
import kotlinx.coroutines.flow.Flow

class PlaceRepository(
    application: Application
) {
    private val placeDao: PlaceDao

    init {
        val db = AppDatabase.getInstance(application)
        placeDao = db.placeDao()
    }

    fun getALlPlaces(): Flow<List<PlaceEntity>> = placeDao.getAllPlaces()
    fun getCurrentPlaces(id: Int): Flow<PlaceEntity> = placeDao.getCurrentPlaces(id)
    fun getSearchQuery(searchQuery: String): Flow<List<PlaceEntity>> = placeDao.getSearchQuery(searchQuery)

    suspend fun insertPlace(place: PlaceEntity){
        placeDao.insert(place)
    }

    suspend fun deletePlace(place: PlaceEntity){
        placeDao.delete(place)
    }

    suspend fun updatePlace(place: PlaceEntity){
        placeDao.update(place)
    }
}