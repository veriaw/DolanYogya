package com.veriaw.dolanyogya.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.veriaw.dolanyogya.data.entity.BookmarkEntity
import com.veriaw.dolanyogya.data.entity.PlaceEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface BookmarkDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(bookmark: BookmarkEntity)

    @Update
    suspend fun update(bookmark: BookmarkEntity)

    @Delete
    suspend fun delete(bookmark: BookmarkEntity)

    @Query("SELECT * from bookmarks where placeId= :placeId and userId= :userId")
    fun isBookmarked(userId: Int, placeId: Int): Flow<BookmarkEntity>

    @Query("SELECT places.* from  bookmarks inner join places on bookmarks.placeId=places.id where bookmarks.userId = :userId")
    fun getCurrentUserBookmark(userId: Int): Flow<List<PlaceEntity>>

    @Query("DELETE FROM bookmarks WHERE userId = :userId AND placeId = :placeId")
    suspend fun deleteBookmark(userId: Int, placeId: Int)
}