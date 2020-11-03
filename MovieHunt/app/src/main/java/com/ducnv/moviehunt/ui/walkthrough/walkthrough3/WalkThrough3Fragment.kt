package com.ducnv.moviehunt.ui.walkthrough.walkthrough3

import com.ducnv.moviehunt.databinding.FragmentWalkthrough3Binding
import com.ducnv.moviehunt.ui.base.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel
import com.ducnv.moviehunt.R

class WalkThrough3Fragment : BaseFragment<FragmentWalkthrough3Binding, WalkThrough3ViewModel>() {

    override val viewModel: WalkThrough3ViewModel by viewModel()

    override val getLayoutId: Int = R.layout.fragment_walkthrough3

}