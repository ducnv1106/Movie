package com.ducnv.moviehunt.ui.home.like


import android.os.Bundle
import androidx.lifecycle.Observer
import com.ducnv.moviehunt.ui.base.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel
import com.ducnv.moviehunt.R
import com.ducnv.moviehunt.databinding.FragmentLikeBinding

class LikeFragment : BaseFragment<FragmentLikeBinding, LikeViewModel>() {

    override val viewModel: LikeViewModel by viewModel()

    override val getLayoutId: Int = R.layout.fragment_like

    private lateinit var adapter: LikeMovieAdapter

    override fun setupView() {
        setUpAdapter()
        viewModel.getListMovie()
    }

    private fun setUpAdapter() {
        adapter = LikeMovieAdapter(fm=this)
        binding.listView.adapter = adapter

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.apply {
            listMovie.observe(viewLifecycleOwner, Observer {
                adapter.submitList(it)
            })
        }

    }


}