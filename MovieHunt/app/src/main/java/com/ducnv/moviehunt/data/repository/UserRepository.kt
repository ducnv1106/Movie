package com.ducnv.moviehunt.data.repository


import com.ducnv.moviehunt.data.remote.ApiService


class UserRepository(private val api: ApiService) {

    suspend fun fetchMovieList(list: String, page: Int?) = api.fetchMovieList(list, page)

    suspend fun fetchSearchMovie(nameMovie: String, page: Int?) =
        api.fetchSearchMovie(nameMovie, page)

    suspend fun fetchCastAndCrew(movie_id: Int) = api.fetchCastAndCrew(movie_id)

    suspend fun fetchToken() = api.fetchToken()

    suspend fun login(username: String, password: String, tokenId: String) =
        api.login(username = username, password = password, request_token = tokenId)

    suspend fun session(tokenId: String) = api.session(request_token = tokenId)

    suspend fun guestSession() = api.guestSession()

    suspend fun fetchFollow(movie_id: Int, session_id: String, guest_session_id: String) =
        api.fetchFollow(
            movie_id = movie_id,
            session_id = session_id,
            guest_session_id = guest_session_id
        )
}