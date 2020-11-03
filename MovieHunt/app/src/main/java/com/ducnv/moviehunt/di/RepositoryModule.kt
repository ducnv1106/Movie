package com.ducnv.moviehunt.di

import android.content.Context
import android.content.SharedPreferences
import com.ducnv.moviehunt.data.local.pref.AppPrefs
import com.ducnv.moviehunt.data.local.pref.PrefHelper
import com.ducnv.moviehunt.data.repository.UserRepository
import org.koin.core.qualifier.named
import org.koin.dsl.module

val repositoryModule= module {

    //repository
    single{ UserRepository(get(named("api")))}

    single{ createSharePrefs(get())}

    // shared prefs
    single{AppPrefs(get())}


}
fun createSharePrefs(context: Context):SharedPreferences=context.getSharedPreferences(context.packageName,Context.MODE_PRIVATE)
