package com.ducnv.moviehunt.data.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ducnv.moviehunt.constants.Constants
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "movie")
data class Movie(
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val adult: Boolean? = null,
    val backdrop_path: String? = null,
    val imdb_id: String? = null,
    val original_language: String? = null,
    val original_title: String? = null,
    val overview: String? = null,
    val popularity: Double? = null,
    val poster_path: String? = null,
    val release_date: String? = null,
    val revenue:Int?=null,
    val runtime:Int?=null,
    val status:String?=null,
    val tagline:String?=null,
    val title:String?,
    val video:Boolean?=null,
    val vote_average:Double?=null,
    val vote_count:Int?=null,
    var isLike:Boolean?=null
) :  Parcelable {
    fun getFullBackdropPath() =
        (if (backdrop_path.isNullOrBlank()) null else Constants.SMALL_IMAGE_URL + backdrop_path)

    fun getPosterPath() =
        if (poster_path.isNullOrBlank()) null else Constants.SMALL_IMAGE_URL + poster_path



}