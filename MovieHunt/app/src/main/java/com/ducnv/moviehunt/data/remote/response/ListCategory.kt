package com.ducnv.moviehunt.data.remote.response

import com.ducnv.moviehunt.data.model.Movie

data class ListCategory(
    val title:String?,
    val data:BaseListResponse<Movie>
)