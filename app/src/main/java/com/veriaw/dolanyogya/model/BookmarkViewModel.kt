package com.veriaw.dolanyogya.model

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.veriaw.dolanyogya.data.BookmarkRepository
import com.veriaw.dolanyogya.data.PlaceRepository
import com.veriaw.dolanyogya.data.entity.BookmarkEntity
import com.veriaw.dolanyogya.data.entity.PlaceEntity
import kotlinx.coroutines.launch

class BookmarkViewModel(application: Application): ViewModel() {
    private val bookmarkRepository: BookmarkRepository = BookmarkRepository(application)

    fun getCurrentBookmark(userId: Int): LiveData<List<PlaceEntity>> = bookmarkRepository.getCurrentUserBookmark(userId).asLiveData()
    fun isBookmarked(userId: Int, placeId: Int): LiveData<BookmarkEntity> = bookmarkRepository.isBookmarked(userId,placeId).asLiveData()

    fun insertBookmark(bookmark: BookmarkEntity){
        viewModelScope.launch {
            bookmarkRepository.insertBookmark(bookmark)
        }
    }

    fun deleteBookmark(userId: Int, placeId: Int){
        viewModelScope.launch {
            bookmarkRepository.deleteBookmark(userId, placeId)
        }
    }

}