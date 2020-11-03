package com.ducnv.moviehunt.ui.home.movie

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.lifecycle.Observer
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import com.ducnv.moviehunt.R

import com.ducnv.moviehunt.databinding.FragmentMovieBinding
import com.ducnv.moviehunt.ui.base.BaseFragment
import com.ducnv.moviehunt.ui.home.HomeFragmentDirections
import com.ducnv.moviehunt.ui.moviedetail.MovieDetailFragment
import com.ducnv.moviehunt.ui.moviedetail.MovieDetailFragmentDirections

import com.ducnv.moviehunt.ui.widgets.banner.LocalDataAdapter
import com.ducnv.moviehunt.utils.hideKeyboardwithoutPopulate
import kotlinx.android.synthetic.main.fragment_movie.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import org.koin.androidx.viewmodel.ext.android.viewModel


class MovieFragment : BaseFragment<FragmentMovieBinding, MovieViewModel>(), MovieListener {

    override val viewModel: MovieViewModel by viewModel()

    override val getLayoutId: Int = R.layout.fragment_movie

    private lateinit var nowMovieAdapter: NowMovieAdapter
    private lateinit var popularMovieAdapter: BaseMovieAdapter
    private lateinit var topRatedMovieAdapter: BaseMovieAdapter
    private lateinit var upcomingMovieAdapter: BaseMovieAdapter


    override fun setupView() {
        viewModel.apply {
            loadData()
        }
        setupRcview()
        binding.listener = this
        onRefreshListener()
        setupRecyclerViewBanner()
        clickOutSideFocus()
        searchClick()

    }


    private fun setupRcview() {

        nowMovieAdapter = NowMovieAdapter()
        popularMovieAdapter = BaseMovieAdapter()
        topRatedMovieAdapter = BaseMovieAdapter()
        upcomingMovieAdapter = BaseMovieAdapter()
        binding.listViewPopular.adapter = popularMovieAdapter
        binding.listViewNowMovie.adapter = nowMovieAdapter
        binding.listViewToprate.adapter = topRatedMovieAdapter
        binding.listViewUpcoming.adapter = upcomingMovieAdapter


    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


        viewModel.apply {

            guestSession.observe(viewLifecycleOwner, Observer {
                putGuestSession(it.guest_session_id)
                Log.e("movie",it.guest_session_id)
            })
            dataNowMovie.observe(viewLifecycleOwner, Observer {
                nowMovieAdapter.submitList(it.results)

            })
            dataPopularMovie.observe(viewLifecycleOwner, Observer {
                popularMovieAdapter.submitList(it.results)

            })
            dataTopRateMovie.observe(viewLifecycleOwner, Observer {
                topRatedMovieAdapter.submitList(it.results)

            })
            dataUpComingMovie.observe(viewLifecycleOwner, Observer {
                upcomingMovieAdapter.submitList(it.results)
                onLoadDataSuccess()

            })


        }
        getMovies()


    }

    /**
     *  goto Movie ViewAll NowPlay
     */

    override fun onClickedViewAllNowPlay() {

        findNavController().navigate(
            HomeFragmentDirections.toNowMovieViewall()
        )

    }

    override fun onClickedViewAllPopular() {
        findNavController().navigate(HomeFragmentDirections.toPopularViewall())


    }

    override fun onClickedViewAllTopRated() {
        findNavController().navigate(HomeFragmentDirections.toTopratedViewall())
    }

    override fun onClickedViewAllUpcoming() {
        findNavController().navigate(HomeFragmentDirections.toUpcomingViewall())
    }

    private fun onRefreshListener() {
        binding.refreshLayout.setOnRefreshListener {
            viewModel.refreshData()

        }
    }

    private fun setupRecyclerViewBanner() {
        binding.listViewbBanner.setAdapter(LocalDataAdapter())
        binding.listViewbBanner.setAutoPlaying(true)

    }


    @SuppressLint("ClickableViewAccessibility")
    private fun clickOutSideFocus() {
        binding.nestedScrollView.setOnTouchListener { v, event ->
            if (search_edit.isFocusable) search_edit.clearFocus()
            v.hideKeyboardwithoutPopulate()
            false
        }
    }


    @SuppressLint("ClickableViewAccessibility")
    private fun searchClick() {
        binding.searchEdit.setOnKeyListener { v, keyCode, event ->
            if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                val key = binding.searchEdit.text.toString()
                if (key.isNotEmpty()) {
                    viewModel.apply {

                        //show dialog loading
                        isLoading.value = true

                        fetchDataSearchMovie(key)

                    }

                }
                search_edit.isCursorVisible = false
                nestedScrollView.hideKeyboardwithoutPopulate()
            }
            false
        }


    }

    /**
     * autocompletesearch
     */

    private fun getMovies() {
        viewModel.apply {
            dataSearchMovie.observe(viewLifecycleOwner, Observer {

                //hide dialog loading
                isLoading.value = false

                val adapter =
                    ArrayAdapter<String>(
                        requireContext(),
                        android.R.layout.simple_spinner_dropdown_item,
                        it.results!!.map { it.title })
                binding.searchEdit.setAdapter(adapter)
                binding.searchEdit.threshold = 2
                binding.searchEdit.showDropDown()
                adapter.notifyDataSetChanged()

            })
        }
    }


}





