package com.ducnv.moviehunt.data.repository


import androidx.room.Dao
import com.ducnv.moviehunt.data.local.dao.MovieDao
import com.ducnv.moviehunt.data.model.Movie
import com.ducnv.moviehunt.data.remote.ApiService


class UserRepository(private val api: ApiService,private val movieDao: MovieDao) {

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

    /**
     * local movie database functions
     */

    suspend fun insertDB(list: List<Movie>){
        movieDao.insert(list)
    }

    suspend fun updateDB(movie:Movie){
        movieDao.update(movie)
    }

    suspend fun getListMovieLocal():List<Movie>?{
       return movieDao.getMovieList()
    }

    suspend fun getMovieLocal(idMovie:String):Movie?{
        return movieDao.getMovie(idMovie)
    }

    suspend fun insertMovieLocal(movie: Movie){
        movieDao.insert(movie)
    }

    suspend fun insertMovieLocal(list: List<Movie>){
        movieDao.insert(list)
    }

    suspend fun updateMovieLocal(movie: Movie){
        movieDao.update(movie)
    }
    suspend fun deleteMovieLocal(movie: Movie){
        movieDao.deleteMovie(movie)
    }

    suspend fun deleteMovieLocal(id:String){
        movieDao.deleteMovie(id)
    }

    suspend fun deleteAllMovieLocal(){
        movieDao.deleteAllMovie()
    }

    suspend fun getMoviePageLocal(pageSize:Int,pageIndex:Int):List<Movie>?{
       return movieDao.getMoviePage(pageSize,pageIndex)
    }

    suspend fun getMovieLikeLocal(pageSize: Int,pageIndex: Int):List<Movie>?{
        return movieDao.getLikeMovie(pageSize,pageIndex)
    }
}