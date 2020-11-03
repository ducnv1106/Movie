package com.ducnv.moviehunt.data.local.pref

import android.content.SharedPreferences
import androidx.core.content.edit

class AppPrefs(private val sharedPreferences: SharedPreferences):PrefHelper{

    companion object {
        private const val FIRST_RUN = "FIRST_RUN"
    }
    override fun clear() {
        sharedPreferences.edit { clear() }
    }

    override fun remove(key:String) {
        sharedPreferences.edit { remove(key) }
    }

    override fun isFirstRun() : Boolean{
        val isFirstRun = sharedPreferences.getBoolean(FIRST_RUN,true)
        if (isFirstRun){
            sharedPreferences.edit { putBoolean(FIRST_RUN,false) }
        }
        return isFirstRun
    }

    override fun put(key: String,value:String) {
        sharedPreferences.edit {
            putString(key, value).commit()
        }
    }

    override fun get(key: String): String? {
        return sharedPreferences.getString(key,null)
    }

}