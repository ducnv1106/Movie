package com.ducnv.moviehunt.data.remote.response

import com.ducnv.moviehunt.data.model.Cast
import com.ducnv.moviehunt.data.model.Crew

data class GetListCastAndCrewResponse(
    val id:Int?=null,
    val cast:ArrayList<Cast>?=null,
    val crew:ArrayList<Crew>?=null
)