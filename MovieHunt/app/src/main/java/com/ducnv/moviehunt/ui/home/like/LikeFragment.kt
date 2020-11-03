package com.ducnv.moviehunt.ui.home.like


import com.ducnv.moviehunt.ui.base.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel
import com.ducnv.moviehunt.R
import com.ducnv.moviehunt.databinding.FragmentLikeBinding

class LikeFragment : BaseFragment<FragmentLikeBinding,LikeViewModel>(){

    override val viewModel: LikeViewModel by viewModel()

    override val getLayoutId: Int = R.layout.fragment_like

}