package com.ducnv.moviehunt.data.local.pref

interface PrefHelper {

    fun clear()

    fun remove(key:String)

    fun isFirstRun() : Boolean

    fun put(key:String,value:String)

    fun get(key: String):String?
}