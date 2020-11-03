package com.ducnv.moviehunt.ui.moviedetail.bottomsheet

import android.os.Bundle
import android.util.Log
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.ducnv.moviehunt.databinding.DialogBottomSheetIntroductionBinding
import com.ducnv.moviehunt.ui.base.BaseBottomSheetDialogFragment
import org.koin.androidx.viewmodel.ext.android.viewModel
import com.ducnv.moviehunt.R
import com.ducnv.moviehunt.utils.setSingleClick
import org.koin.android.ext.android.bind
import org.koin.android.ext.android.inject

class IntroductionBottomSheet :
    BaseBottomSheetDialogFragment<DialogBottomSheetIntroductionBinding, IntroductionBottomSheetViewMode>() {

    override val viewModel: IntroductionBottomSheetViewMode by viewModel()

    override val layoutId: Int = R.layout.dialog_bottom_sheet_introduction

    private val args: IntroductionBottomSheetArgs by navArgs()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel.apply {
            args.movie.let {
                this.movie.value = it
            }
        }
        dialog?.window?.attributes?.windowAnimations=R.style.MyDialogAnimation
        onCloseBottomSheet()

    }


    private fun onCloseBottomSheet() {
        viewBinding.imgClose.setSingleClick {
            findNavController().navigateUp()
        }
    }

    override fun onResume() {
        super.onResume()

//
    }


    override fun onStop() {
        super.onStop()
        dialog?.window?.setWindowAnimations(-1)
    }
}