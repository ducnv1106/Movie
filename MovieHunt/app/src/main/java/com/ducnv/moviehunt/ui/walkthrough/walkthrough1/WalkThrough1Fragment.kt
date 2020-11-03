package com.ducnv.moviehunt.ui.walkthrough.walkthrough1

import android.os.Bundle
import android.view.WindowManager
import com.ducnv.moviehunt.databinding.FragmentWalkthrough1Binding
import com.ducnv.moviehunt.ui.base.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel
import com.ducnv.moviehunt.R

class WalkThrough1Fragment : BaseFragment<FragmentWalkthrough1Binding,WalkThrough1ViewModel>(){

    override val viewModel: WalkThrough1ViewModel by viewModel()

    override val getLayoutId: Int = R.layout.fragment_walkthrough1

    override fun onCreate(savedInstanceState: Bundle?) {
//        activity?.window?.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
        super.onCreate(savedInstanceState)
    }


}