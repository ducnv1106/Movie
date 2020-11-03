package com.ducnv.moviehunt.ui.moviedetail.bottomsheet

import androidx.lifecycle.MutableLiveData
import com.ducnv.moviehunt.data.model.Movie
import com.ducnv.moviehunt.ui.base.BaseViewModel

class IntroductionBottomSheetViewMode : BaseViewModel() {

    val movie = MutableLiveData<Movie>()
}