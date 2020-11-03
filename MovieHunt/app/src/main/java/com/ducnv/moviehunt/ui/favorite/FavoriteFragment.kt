package com.ducnv.moviehunt.ui.favorite

import com.ducnv.moviehunt.databinding.FragmentFavoriteBinding
import com.ducnv.moviehunt.ui.base.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel
import com.ducnv.moviehunt.R

class FavoriteFragment : BaseFragment<FragmentFavoriteBinding,FavoriteViewModel>(){

    override val viewModel: FavoriteViewModel by viewModel()

    override val getLayoutId: Int = R.layout.fragment_favorite


}