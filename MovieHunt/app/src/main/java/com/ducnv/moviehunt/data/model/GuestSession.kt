package com.ducnv.moviehunt.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class GuestSession (
    val success:Boolean,
    val guest_session_id:String,
    val expires_at:String
):Parcelable