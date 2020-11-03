package com.ducnv.moviehunt.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Token(
    val success:Boolean?=null,
    val expires_at:String?=null,
    val request_token:String?=null
):Parcelable