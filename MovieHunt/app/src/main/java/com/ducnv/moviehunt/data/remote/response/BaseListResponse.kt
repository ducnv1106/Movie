package com.ducnv.moviehunt.data.remote.response

import com.google.gson.annotations.SerializedName
import java.util.ArrayList

data class BaseListResponse<Item>(
    @SerializedName("page")
    val page:Int?=null,
    @SerializedName("total_results")
    val totalResults:Int?=null,
    @SerializedName("total_pages")
    val totalPage:Int?=null,
    @SerializedName("results")
    val results: ArrayList<Item>?=null
) : BaseResponse()