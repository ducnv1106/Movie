package com.ducnv.moviehunt.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class Following (
    val id:Int,
    val favorite:Boolean,
    val rated:Boolean?,
    val watchlist:Boolean

):Parcelable{

}