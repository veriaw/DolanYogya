package com.veriaw.dolanyogya.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.veriaw.dolanyogya.data.entity.PlaceEntity
import com.veriaw.kriptografiapp.data.entity.UserEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PlaceDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(place: PlaceEntity)

    @Update
    suspend fun update(place: PlaceEntity)

    @Delete
    suspend fun delete(place: PlaceEntity)

    @Query("SELECT * from places")
    fun getAllPlaces(): Flow<List<PlaceEntity>>

    @Query("SELECT * from  places where id = :id")
    fun getCurrentPlaces(id: Int): Flow<PlaceEntity>

    @Query("SELECT * FROM places WHERE place_Name LIKE :searchQuery;")
    fun getSearchQuery(searchQuery: String): Flow<List<PlaceEntity>>
}