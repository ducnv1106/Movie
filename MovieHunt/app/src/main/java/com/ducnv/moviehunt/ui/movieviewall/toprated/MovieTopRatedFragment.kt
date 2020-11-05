package com.ducnv.moviehunt.ui.movieviewall.toprated

import com.ducnv.moviehunt.ui.movieviewall.popular.MovieViewAllAdapter


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ducnv.moviehunt.R
import com.ducnv.moviehunt.data.model.Movie
import com.ducnv.moviehunt.databinding.FragmentLoadmoreRefreshBinding
import com.ducnv.moviehunt.ui.base.BaseListAdapter
import com.ducnv.moviehunt.ui.base.BaseLoadMoreRefreshFragment
import com.ducnv.moviehunt.ui.movieviewall.nowplay.MovieNowPlayFragmentDirections
import com.ducnv.moviehunt.ui.movieviewall.popular.MoviePopularFragmentDirections
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class MovieTopRatedFragment :
    BaseLoadMoreRefreshFragment<FragmentLoadmoreRefreshBinding, MovieTopRatedViewModel, Movie>() {
    override val listAdapter: BaseListAdapter<Movie, out ViewDataBinding> by lazy {
        MovieViewAllAdapter(
            fm = this,
            itemClickListener = { movie: Movie, imageUrl: ImageView ->
                toMovieDetail(
                    movie = movie,
                    imageView = imageUrl
                )
            })
    }

    override val viewModel: MovieTopRatedViewModel by viewModel()

    private val activityScope = CoroutineScope(Dispatchers.Main)

    override fun getLayoutManager(): RecyclerView.LayoutManager {
        return GridLayoutManager(context, 2)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.apply {
            isLoadMore.observe(viewLifecycleOwner, Observer {
                if (it) binding.progressLoadMore.visibility = View.VISIBLE
                else {
                    activityScope.launch {
                        delay(1000)
                        binding.progressLoadMore.visibility = View.GONE
                    }

                }
            })

        }

    }

    override fun setupView() {
        binding.tvTitle.setText(R.string.category_top_rated_list)
        onBackClickListener()
    }

    private fun onBackClickListener() {
        binding.icBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun toMovieDetail(movie: Movie, imageView: ImageView) {
        val extras = FragmentNavigatorExtras(
            imageView to movie.id
        )
        Log.e("movie", movie.id)

        findNavController().navigate(MovieNowPlayFragmentDirections.toMovieDetail(movie), extras)
    }


}