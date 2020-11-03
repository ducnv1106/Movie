package com.ducnv.moviehunt.ui.rating

import com.ducnv.moviehunt.databinding.FragmentRatingBinding
import com.ducnv.moviehunt.ui.base.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel
import com.ducnv.moviehunt.R

class RatingFragment : BaseFragment<FragmentRatingBinding,RatingViewModel>(){

    override val viewModel: RatingViewModel by viewModel()

    override val getLayoutId: Int = R.layout.fragment_rating

}