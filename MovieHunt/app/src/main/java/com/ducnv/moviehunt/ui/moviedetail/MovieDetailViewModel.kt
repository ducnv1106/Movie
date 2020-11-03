package com.ducnv.moviehunt.ui.moviedetail

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ducnv.moviehunt.constants.Constants
import com.ducnv.moviehunt.data.local.pref.AppPrefs
import com.ducnv.moviehunt.data.repository.UserRepository
import com.ducnv.moviehunt.data.model.Cast
import com.ducnv.moviehunt.data.model.Following
import com.ducnv.moviehunt.data.model.Movie
import com.ducnv.moviehunt.data.remote.response.GetListCastAndCrewResponse
import com.ducnv.moviehunt.ui.base.BaseViewModel
import kotlinx.coroutines.launch

class MovieDetailViewModel(
    private val userRepository: UserRepository,
    private val prefs: AppPrefs
) : BaseViewModel() {

    val movie = MutableLiveData<Movie>()

    val cast = MutableLiveData<ArrayList<Cast>>()

    val following = MutableLiveData<Following>()

    val isLike = MutableLiveData<Boolean>().apply { value = false }

    val isRefreshing = MutableLiveData<Boolean>().apply { value = false }


    fun fecthFollow(movieId: Int) {


        viewModelScope.launch {

            try {
                following.value = userRepository.fetchFollow(
                    movie_id = movieId,
                    session_id = getSessionID() ?: "null",
                    guest_session_id = getGuestSession() ?: "null"
                )
            } catch (e: Exception) {
                onError(e)
            }
        }
    }

    /**
     * get data cast and crew
     */
    private fun fetchCastAndCrew(movieId: Int) {
        viewModelScope.launch {
            try {
                cast.value = userRepository.fetchCastAndCrew(movie_id = movieId).cast
            } catch (e: Exception) {
                isRefreshing.value = false
                onError(e)
            }
        }
    }

    fun loadData(movieId: Int) {
        // show loading dialog  when the begin in screen
        if (isRefreshing.value == false) {
            showLoading()
        }
        fetchCastAndCrew(movieId)

        fecthFollow(movieId)
    }

    fun refreshData(movieId: Int) {
        if (isRefreshing.value == true || isLoading.value == true) return
        isRefreshing.value = true
        loadData(movieId)
    }

    fun loadDataSuccess() {

        /**
         * reset refreshing and loading
         */
        isRefreshing.value = false
        isLoading.value = false
    }

    fun getSessionID(): String? = prefs.get(Constants.SESSION_ID)

    fun getGuestSession(): String? = prefs.get(Constants.GUEST_SESSION)

}