package com.ducnv.moviehunt.ui.image

import androidx.lifecycle.MutableLiveData
import com.ducnv.moviehunt.data.model.Cast
import com.ducnv.moviehunt.ui.base.BaseViewModel

class ImageViewModel : BaseViewModel(){

    val imageUrl=MutableLiveData<String>()
}