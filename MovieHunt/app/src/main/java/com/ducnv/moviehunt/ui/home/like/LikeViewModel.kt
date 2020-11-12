package com.ducnv.moviehunt.ui.home.like

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ducnv.moviehunt.data.model.Movie
import com.ducnv.moviehunt.data.repository.UserRepository
import com.ducnv.moviehunt.ui.base.BaseViewModel
import kotlinx.coroutines.launch

class LikeViewModel(private val userRepository: UserRepository) : BaseViewModel(){

    val listMovie=MutableLiveData<List<Movie>>()

    fun getListMovie(){

        viewModelScope.launch {
            try {
                listMovie.value=userRepository.getListMovieLocal()
            }catch (e:Exception){
                onError(e)
            }
        }
    }

}