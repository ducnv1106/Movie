package com.ducnv.moviehunt.ui.home.tv

import android.util.Log
import com.ducnv.moviehunt.databinding.FragmentTvBinding
import com.ducnv.moviehunt.ui.base.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel
import com.ducnv.moviehunt.R
import com.ducnv.moviehunt.ui.widgets.searchview.JJBarWithErrorIconController

class TvFragment : BaseFragment<FragmentTvBinding, TvViewModel>() {

    override val viewModel: TvViewModel by viewModel()

    override val getLayoutId: Int = R.layout.fragment_tv

    override fun setupView() {
        setupSearchView()

    }

    private fun setupSearchView() {

    }

}