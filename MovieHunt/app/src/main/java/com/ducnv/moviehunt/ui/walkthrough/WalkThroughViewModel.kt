package com.ducnv.moviehunt.ui.walkthrough

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ducnv.moviehunt.constants.Constants
import com.ducnv.moviehunt.data.local.pref.AppPrefs
import com.ducnv.moviehunt.data.model.Session
import com.ducnv.moviehunt.ui.base.BaseViewModel
import kotlinx.coroutines.launch

class WalkThroughViewModel(private val appPrefs: AppPrefs) : BaseViewModel(){

    val sessionId=MutableLiveData<String>().apply {
        this.value=appPrefs.get(Constants.SESSION_ID)
    }

}