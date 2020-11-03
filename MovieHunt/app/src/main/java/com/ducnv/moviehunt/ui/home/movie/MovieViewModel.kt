package com.ducnv.moviehunt.ui.home.movie

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ducnv.moviehunt.constants.Constants
import com.ducnv.moviehunt.constants.MovieCategory
import com.ducnv.moviehunt.data.local.pref.AppPrefs
import com.ducnv.moviehunt.data.model.GuestSession
import com.ducnv.moviehunt.data.repository.UserRepository
import com.ducnv.moviehunt.data.model.Movie
import com.ducnv.moviehunt.data.remote.response.BaseListResponse
import com.ducnv.moviehunt.ui.base.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch


class MovieViewModel(private val userRepository: UserRepository,private val prefs:AppPrefs) : BaseViewModel() {

    val guestSession = MutableLiveData<GuestSession>()

    val dataNowMovie=MutableLiveData<BaseListResponse<Movie>>()

    val dataPopularMovie=MutableLiveData<BaseListResponse<Movie>>()

    val dataTopRateMovie=MutableLiveData<BaseListResponse<Movie>>()

    val dataUpComingMovie=MutableLiveData<BaseListResponse<Movie>>()

    val dataSearchMovie = MutableLiveData<BaseListResponse<Movie>>()

    // refresh
    val isRefreshing = MutableLiveData<Boolean>().apply { value=false }

    val isSkeleton = MutableLiveData<Boolean>().apply { value=true }


    fun fetchGuestSession(){
        viewModelScope.launch {
            try {
                guestSession.value=userRepository.guestSession()
            }catch (e:Exception){
                Log.e("guesSession","error")
            }
        }
    }
    private fun fetchDataMovie(){

        viewModelScope.launch {
            try {
                dataNowMovie.value=userRepository.fetchMovieList(list = MovieCategory.NOW_PLAYING.key,page = 1)
                dataPopularMovie.value=userRepository.fetchMovieList(list =MovieCategory.POPULAR.key,page = 1)
                dataTopRateMovie.value=userRepository.fetchMovieList(list =MovieCategory.TOP_RATED.key,page = 1)
                dataUpComingMovie.value=userRepository.fetchMovieList(list =MovieCategory.UPCOMING.key,page = 1)
                guestSession.value=userRepository.guestSession()

            }catch (e:Exception){
                isRefreshing.value=false
                onError(e)
            }
        }
    }
    fun fetchDataSearchMovie(nameMovie:String){
        viewModelScope.launch {
            try {
                dataSearchMovie.value=userRepository.fetchSearchMovie(nameMovie,null)
            }catch (e:Exception){
                onError(e)
            }
        }
    }

    fun loadData(){
        // show loading dialog  when the begin in screen
        if (isRefreshing.value==false) showLoading()
        fetchDataMovie()
    }
    fun refreshData(){
        if(isRefreshing.value==true || isLoading.value==true) return
        isRefreshing.value=true
        isSkeleton.value=true
        loadData()
    }

    fun onLoadDataSuccess(){
        /**
         * reset refresh and loading
         */
        isRefreshing.value=false
        isLoading.value=false
        isSkeleton.value=false

    }

    fun putGuestSession(value: String){
        prefs.put(Constants.GUEST_SESSION,value=value)
    }



}