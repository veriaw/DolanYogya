package com.veriaw.dolanyogya.data.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "bookmarks")
@Parcelize
class BookmarkEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int =0,

    @ColumnInfo(name = "userId")
    var userId: Int =0,

    @ColumnInfo(name = "placeId")
    var placeId: Int =0,
    ):Parcelable