package com.ducnv.moviehunt.constants

import java.lang.Boolean
import java.lang.Boolean.parseBoolean


object Constants {
    val DEBUG = parseBoolean("true")
    const val DEFAULT_FIRST_PAGE = 1
    const val DEFAULT_NUM_VISIBLE_THRESHOLD = 3
    const val DEFAULT_ITEM_PER_PAGE = 10
    const val THRESHOLD_CLICK_TIME = 1000
    const val MIN_PASSWORD_LENGTH = 8

    const val DATABASE_NAME = "DATABASE_NAME"

    const val API_ROOT = "https://api.themoviedb.org/3/"
    const val IMAGE_API_ROOT = "https://image.tmdb.org/t/p/"
    const val TMDB_API_KEY = "b80639b9f5a2e4e880b931dedbec575b"
    const val SMALL_IMAGE_URL="https://image.tmdb.org/t/p/w200"

    const val TOKEN="TOKEN"
    const val SESSION_ID="SESSION_ID"
    const val GUEST_SESSION="GUEST_SESSION"

}