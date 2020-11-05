package com.ducnv.moviehunt.ui.movieviewall.nowplay

import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import android.transition.TransitionInflater
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.app.SharedElementCallback
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.ducnv.moviehunt.R
import com.ducnv.moviehunt.data.model.Movie
import com.ducnv.moviehunt.databinding.FragmentLoadmoreRefreshBinding
import com.ducnv.moviehunt.ui.base.BaseListAdapter
import com.ducnv.moviehunt.ui.base.BaseLoadMoreRefreshFragment
import com.ducnv.moviehunt.ui.home.HomeActivity
import kotlinx.coroutines.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class MovieNowPlayFragment :
    BaseLoadMoreRefreshFragment<FragmentLoadmoreRefreshBinding, MovieNowPlayViewModel, Movie>() {
    override val listAdapter: BaseListAdapter<Movie, out ViewDataBinding> by lazy {
        NowPlayMovieAdapter(
            fm = this,
            itemClickListener = { movie: Movie, imageView: ImageView ->
                toMovieDetail(
                    movie,
                    imageView
                )
            })
    }



    override val viewModel: MovieNowPlayViewModel by viewModel()

    override fun getLayoutManager(): RecyclerView.LayoutManager {
        return GridLayoutManager(context, 2)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.apply {
            isLoadMore.observe(viewLifecycleOwner, Observer {
                if (it) binding.progressLoadMore.visibility = View.VISIBLE
                else binding.progressLoadMore.visibility = View.GONE
            })

        }


    }

    override fun setupView() {
        onBackClickListener()

    }

    private fun onBackClickListener() {
        binding.icBack.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun toMovieDetail(movie: Movie, imageView: ImageView) {
        val extras = FragmentNavigatorExtras(
            imageView to movie.id
        )
        findNavController().navigate(MovieNowPlayFragmentDirections.toMovieDetail(movie), extras)
    }




}

