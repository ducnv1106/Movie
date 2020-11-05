package com.ducnv.moviehunt.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ducnv.moviehunt.data.local.dao.MovieDao
import com.ducnv.moviehunt.data.model.Movie

@Database(entities = [Movie::class],version = 1,exportSchema = false)
abstract class AppDatabase : RoomDatabase(){

    abstract fun movieDao():MovieDao
}