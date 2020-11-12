package com.ducnv.moviehunt.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue

@Parcelize
class Following (
    val id:Int,
    val favorite:Boolean,
    val rated:Rated,
    val watchlist:Boolean
):Parcelable{

}