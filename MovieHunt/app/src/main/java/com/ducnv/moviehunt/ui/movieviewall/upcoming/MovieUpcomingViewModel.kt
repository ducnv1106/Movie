package com.ducnv.moviehunt.ui.movieviewall.upcoming

import androidx.lifecycle.viewModelScope
import com.ducnv.moviehunt.constants.MovieCategory
import com.ducnv.moviehunt.data.repository.UserRepository
import com.ducnv.moviehunt.data.model.Movie
import com.ducnv.moviehunt.ui.base.BaseLoadMoreRefreshViewModel
import kotlinx.coroutines.launch

class MovieUpcomingViewModel(private val userRepository: UserRepository) : BaseLoadMoreRefreshViewModel<Movie>(){

    override fun loadData(page: Int) {
        viewModelScope.launch {
            try {
                onLoadSuccess(page=page,items = userRepository.fetchMovieList(list = MovieCategory.UPCOMING.key,page = page).results)
            }catch(e:Exception){
                onError(e)
            }
        }
    }

}