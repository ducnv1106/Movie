package com.ducnv.moviehunt.ui.login

import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ducnv.moviehunt.constants.Constants
import com.ducnv.moviehunt.data.local.pref.AppPrefs
import com.ducnv.moviehunt.data.model.GuestSession
import com.ducnv.moviehunt.data.model.Session
import com.ducnv.moviehunt.data.model.Token
import com.ducnv.moviehunt.data.repository.UserRepository
import com.ducnv.moviehunt.di.viewModelModule
import com.ducnv.moviehunt.ui.base.BaseViewModel
import kotlinx.coroutines.launch
import kotlin.math.log

class LoginViewModel(private val userRepository: UserRepository, private val prefs: AppPrefs) :
    BaseViewModel() {

    val token = MutableLiveData<Token>()

    val login = MutableLiveData<Token>()

    val session = MutableLiveData<Session>()





    fun fetchSession(tokenId: String) {

        viewModelScope.launch {
            try {
                session.value = userRepository.session(tokenId)
            } catch (e: Exception) {
                onError(e)
            }
        }
    }

    fun fetchToken() {
        viewModelScope.launch {
            try {
                token.value = userRepository.fetchToken()
            } catch (e: Exception) {
                onError(e)
            }
        }
    }

    fun login(username: String, password: String) {
        viewModelScope.launch {
            try {
                login.value = userRepository.login(username, password, getTokenId() ?: "aa")
            } catch (e: Exception) {
                onError(e)
            }
        }
    }

    fun checkIsToken(): Boolean {
        return prefs.get(Constants.TOKEN).isNullOrBlank()
    }

    fun putTokenId(value: String) {
        prefs.put(key = Constants.TOKEN, value = value)
    }

    fun getTokenId(): String? = prefs.get(key = Constants.TOKEN)

    fun putSessionId(value: String) {
        prefs.put(Constants.SESSION_ID, value = value)
    }


    fun loadData(username: String, password: String) {
        login(username, password)
        showLoading()
    }


}