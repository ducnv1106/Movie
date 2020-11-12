package com.ducnv.moviehunt.ui.moviedetail.dialogfragment.favorite

import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.content.ContextCompat
import com.ducnv.moviehunt.databinding.DialogFragmentLikeMovieBinding
import com.ducnv.moviehunt.ui.base.BaseDialogFragment
import com.ducnv.moviehunt.R
import com.github.razir.progressbutton.attachTextChangeAnimator
import com.github.razir.progressbutton.bindProgressButton
import com.github.razir.progressbutton.showDrawable
import com.github.razir.progressbutton.showProgress
import org.koin.androidx.viewmodel.ext.android.viewModel

class FavoriteMovieDialogFragment : BaseDialogFragment<DialogFragmentLikeMovieBinding,FavoriteMovieDialogViewModel>(){

    override val viewModel: FavoriteMovieDialogViewModel by viewModel()

    override val layoutId: Int = R.layout.dialog_fragment_like_movie

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewBinding.buttonProgressMixed.setBackgroundResource(R.drawable.bg_materiakbutton_favorite_movie)
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
                buttonTextRes = R.string.success
            }
            Handler().postDelayed({

                this.dismiss()
            }, 2000)
        }, 3000)

    }

}