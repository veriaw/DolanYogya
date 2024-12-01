package com.veriaw.dolanyogya.data.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "places")
@Parcelize
data class PlaceEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int =0,

    @ColumnInfo(name = "place_Name")
    var placeName: String? =null,

    @ColumnInfo(name = "description")
    var description: String? =null,

    @ColumnInfo(name = "category")
    var category: String? =null,

    @ColumnInfo(name = "city")
    var city: String? =null,

    @ColumnInfo(name = "price")
    var price: Int? =null,

    @ColumnInfo(name = "rating_avg")
    var rating_avg: Int? =null,

    @ColumnInfo(name = "latitude")
    var latitude: Double? =null,

    @ColumnInfo(name = "longitude")
    var longitude: Double? =null,

    @ColumnInfo(name = "pictureUrl")
    var pictureUrl: String? =null,
):Parcelable