package com.ducnv.moviehunt.ui.moviedetail.dialogfragment.like


import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.widget.Button
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.navArgs
import org.koin.androidx.viewmodel.ext.android.viewModel
import com.ducnv.moviehunt.R
import com.ducnv.moviehunt.databinding.DialogFragmentLikeMovieBinding
import com.ducnv.moviehunt.ui.base.BaseDialogFragment
import com.github.razir.progressbutton.*

class LikeMovieDialogFragment :
    BaseDialogFragment<DialogFragmentLikeMovieBinding, LikeMovieDialogViewModel>() {

    override val viewModel: LikeMovieDialogViewModel by viewModel()

    override val layoutId: Int = R.layout.dialog_fragment_like_movie

    private val args: LikeMovieDialogFragmentArgs by navArgs()


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewBinding.buttonProgressMixed.attachTextChangeAnimator()
        bindProgressButton(viewBinding.buttonProgressMixed)
        showMixed(viewBinding.buttonProgressMixed)

    }

    private fun showMixed(button: Button) {

        val animatedDrawable =
            ContextCompat.getDrawable(requireContext(), R.drawable.animated_check)!!
        //Defined bounds are required for your drawable
        val drawableSize = resources.getDimensionPixelSize(R.dimen.doneSize)
        animatedDrawable.setBounds(0, 0, drawableSize, drawableSize)

        button.showProgress {
            buttonTextRes = R.string.loading
            progressColor = Color.WHITE
        }
        Handler().postDelayed({
            button.isEnabled = true
            button.showDrawable(animatedDrawable) {
                buttonTextRes = args.request
            }
            Handler().postDelayed({

                this.dismiss()
            }, 2000)
        }, 3000)

    }

}