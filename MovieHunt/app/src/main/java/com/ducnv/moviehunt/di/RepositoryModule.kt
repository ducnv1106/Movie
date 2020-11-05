package com.ducnv.moviehunt.di

import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import com.ducnv.moviehunt.constants.Constants
import com.ducnv.moviehunt.data.local.db.AppDatabase
import com.ducnv.moviehunt.data.local.pref.AppPrefs
import com.ducnv.moviehunt.data.repository.UserRepository
import org.koin.core.qualifier.named
import org.koin.dsl.module

val repositoryModule = module {

    //database
    single { createAppDatabase(get()) }
    single { createMovieDao(get()) }



    single { createSharePrefs(get()) }

    // shared prefs
    single { AppPrefs(get()) }

    //repository
    single { UserRepository(get(named("api")), get()) }


}

fun createSharePrefs(context: Context): SharedPreferences =
    context.getSharedPreferences(context.packageName, Context.MODE_PRIVATE)

fun createAppDatabase(context: Context): AppDatabase =
    Room.databaseBuilder(context, AppDatabase::class.java, Constants.DATABASE_NAME).build()

fun createMovieDao(appDatabase: AppDatabase) = appDatabase.movieDao()
