package com.ducnv.moviehunt.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Session(
    val success:Boolean,
    val session_id:String
) : Parcelable