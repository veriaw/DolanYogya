package com.veriaw.kriptografiapp.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.veriaw.dolanyogya.data.entity.BookmarkEntity
import com.veriaw.dolanyogya.data.entity.PlaceEntity
import com.veriaw.dolanyogya.data.local.BookmarkDao
import com.veriaw.dolanyogya.data.local.PlaceDao
import com.veriaw.kriptografiapp.data.entity.UserEntity

@Database(entities = [UserEntity::class, PlaceEntity::class, BookmarkEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase: RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun placeDao(): PlaceDao
    abstract fun bookmarkDao(): BookmarkDao

    companion object{
        @Volatile
        private var instance: AppDatabase? = null
        fun getInstance(context: Context):AppDatabase =
            instance?: synchronized(this){
                instance?: Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,"dolanyogyadb.db"
                ).build()
            }
    }
}