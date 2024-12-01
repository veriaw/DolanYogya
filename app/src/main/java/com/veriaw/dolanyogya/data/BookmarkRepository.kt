package com.veriaw.dolanyogya.data

import android.app.Application
import com.veriaw.dolanyogya.data.entity.BookmarkEntity
import com.veriaw.dolanyogya.data.entity.PlaceEntity
import com.veriaw.dolanyogya.data.local.BookmarkDao
import com.veriaw.dolanyogya.data.local.PlaceDao
import com.veriaw.kriptografiapp.data.local.AppDatabase
import kotlinx.coroutines.flow.Flow

class BookmarkRepository (
    application: Application
) {
    private val bookmarkDao: BookmarkDao

    init {
        val db = AppDatabase.getInstance(application)
        bookmarkDao = db.bookmarkDao()
    }

    fun getCurrentUserBookmark(userId: Int): Flow<List<PlaceEntity>> = bookmarkDao.getCurrentUserBookmark(userId)
    fun isBookmarked(userId: Int, placeId: Int): Flow<BookmarkEntity> = bookmarkDao.isBookmarked(userId, placeId)

    suspend fun insertBookmark(bookmark: BookmarkEntity){
        bookmarkDao.insert(bookmark)
    }

    suspend fun deleteBookmark(userId: Int, placeId: Int){
        bookmarkDao.deleteBookmark(userId, placeId)
    }

}