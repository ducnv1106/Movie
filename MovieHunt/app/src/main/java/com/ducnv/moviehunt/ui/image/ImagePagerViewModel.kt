package com.ducnv.moviehunt.ui.image

import androidx.lifecycle.MutableLiveData
import com.ducnv.moviehunt.data.model.Cast
import com.ducnv.moviehunt.data.model.Movie
import com.ducnv.moviehunt.ui.base.BaseViewModel

class ImagePagerViewModel : BaseViewModel(){

    val listCast=MutableLiveData<Array<String>>()

}