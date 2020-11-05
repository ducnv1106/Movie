package com.ducnv.moviehunt.data.local.dao

import androidx.room.*
import androidx.room.OnConflictStrategy.IGNORE
import androidx.room.OnConflictStrategy.REPLACE
import com.ducnv.moviehunt.data.model.Movie

@Dao
interface MovieDao{

    @Query("SELECT * FROM movie")
    suspend fun getMovieList():List<Movie>?

    @Query("SELECT * FROM movie WHERE movie.id=:id")
    suspend fun getMovie(id:String):Movie?

    @Insert(onConflict = IGNORE)
    suspend fun insert(movie: Movie)

    @Insert(onConflict = IGNORE)
    suspend fun  insert(list:List<Movie>)

    @Insert(onConflict = REPLACE)
    suspend fun update(movie: Movie)

    @Delete
    suspend fun deleteMovie(movie: Movie)

    @Query("DELETE FROM movie WHERE id=:id")
    suspend fun deleteMovie(id:String)

    @Query("DELETE FROM movie")
    suspend fun deleteAllMovie()

    @Query("SELECT * FROM movie LIMIT :pageSize OFFSET :pageIndex")
    suspend fun getMoviePage(pageSize: Int, pageIndex: Int): List<Movie>?

    @Query("SELECT * FROM movie WHERE movie.isLike = 1 LIMIT :pageSize OFFSET ((:pageIndex-1)*:pageSize) ")
    suspend fun getLikeMovie(pageSize: Int, pageIndex: Int): List<Movie>?

}