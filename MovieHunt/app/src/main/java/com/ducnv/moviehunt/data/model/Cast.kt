package com.ducnv.moviehunt.data.model

import android.os.Parcelable
import com.ducnv.moviehunt.BuildConfig
import com.ducnv.moviehunt.constants.Constants
import kotlinx.android.parcel.Parcelize


@Parcelize
data class Cast(
    val cast_id: String?,
    val character: String?,
    val credit_id: String?,
    val gender: Int?,
    val id: String?,
    val name: String?,
    val order: Int?,
    val profile_path: String?
) : Parcelable {
    fun getFullProfilePath() = if(profile_path.isNullOrBlank()) null else Constants.SMALL_IMAGE_URL+profile_path
}