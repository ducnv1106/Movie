package com.ducnv.moviehunt.data.remote

import com.ducnv.moviehunt.data.model.*
import com.ducnv.moviehunt.data.remote.response.BaseListResponse
import com.ducnv.moviehunt.data.remote.response.GetListCastAndCrewResponse
import retrofit2.http.*

interface ApiService {
    @GET("movie/{list}")
    suspend fun fetchMovieList(
        @Path("list") list: String,
        @Query("page") page: Int?
    ): BaseListResponse<Movie>

    @GET("search/movie")
    suspend fun fetchSearchMovie(
        @Query("query") nameMovie: String,
        @Query("page") page: Int?
    ): BaseListResponse<Movie>

    @GET("movie/{movie_id}/credits")
    suspend fun fetchCastAndCrew(@Path("movie_id") movie_id: Int): GetListCastAndCrewResponse

    @GET("authentication/token/new")
    suspend fun fetchToken(): Token

    @POST("authentication/token/validate_with_login")
    @FormUrlEncoded
    suspend fun login(
        @Field("username") username: String,
        @Field("password") password: String,
        @Field("request_token") request_token: String
    ): Token

    @POST("authentication/session/new")
    @FormUrlEncoded
    suspend fun session(
        @Field("request_token") request_token: String
    ): Session

    @GET("authentication/guest_session/new")
    suspend fun guestSession(): GuestSession

    @GET("movie/{movie_id}/account_states")
    suspend fun fetchFollow(
        @Path("movie_id") movie_id: Int,
        @Query("session_id") session_id: String,
        @Query("guest_session_id") guest_session_id: String
    ):Following

}

object ApiParams {
    const val PAGE = "page"
    const val SORT_BY = "sort_by"
    const val POPULARITY_DESC = "popularity.desc"
    const val VOTE_AVERAGE_DESC = "vote_average.desc"
}