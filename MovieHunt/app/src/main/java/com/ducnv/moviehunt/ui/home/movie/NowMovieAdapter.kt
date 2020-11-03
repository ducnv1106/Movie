package com.ducnv.moviehunt.ui.home.movie

import androidx.recyclerview.widget.DiffUtil
import com.ducnv.moviehunt.R
import com.ducnv.moviehunt.data.model.Movie
import com.ducnv.moviehunt.databinding.ItemMovieNowBinding
import com.ducnv.moviehunt.ui.base.BaseListAdapter

class NowMovieAdapter : BaseListAdapter<Movie,ItemMovieNowBinding>(object :DiffUtil.ItemCallback<Movie>(){

    override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
      return oldItem.id==newItem.id
    }

    override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
       return oldItem==newItem
    }

}) {
    override fun getLayoutRes(viewType: Int): Int {
        return R.layout.item_movie_now
    }

}