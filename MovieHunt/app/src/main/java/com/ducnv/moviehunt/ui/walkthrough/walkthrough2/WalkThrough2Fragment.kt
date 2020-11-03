package com.ducnv.moviehunt.ui.walkthrough.walkthrough2

import com.ducnv.moviehunt.databinding.FragmentWalkthrough2Binding
import com.ducnv.moviehunt.ui.base.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel
import com.ducnv.moviehunt.R


class WalkThrough2Fragment : BaseFragment<FragmentWalkthrough2Binding,WalkThrough2ViewModel>(){

    override val viewModel: WalkThrough2ViewModel by viewModel()

    override val getLayoutId: Int = R.layout.fragment_walkthrough2

}