package com.ducnv.moviehunt.ui.image

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.ducnv.moviehunt.R
import com.ducnv.moviehunt.data.model.Cast
import com.ducnv.moviehunt.databinding.FragmentImageBinding
import com.ducnv.moviehunt.ui.base.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel
import com.ducnv.moviehunt.BR

class ImageFragment : BaseFragment<FragmentImageBinding, ImageViewModel>() {

    override val viewModel: ImageViewModel by viewModel()

    override val getLayoutId: Int = R.layout.fragment_image

    private lateinit var imageUrl: String

    companion object {
        const val KEY_IMAGE = "com.ducnv.movie.hunt"
        fun newInstance(imageUrl: String):ImageFragment{
            val fragment = ImageFragment()
            val argument = Bundle()
            argument.putString(KEY_IMAGE,imageUrl)
            fragment.arguments = argument
            return fragment
        }
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        imageUrl = arguments?.get(KEY_IMAGE) as String

        viewModel.apply {
            this@ImageFragment.imageUrl?.let {
                this.imageUrl.value=it
            }
        }
        val requestListener=object : RequestListener<Drawable>{
            override fun onLoadFailed(
                e: GlideException?,
                model: Any?,
                target: Target<Drawable>?,
                isFirstResource: Boolean
            ): Boolean {
                if (parentFragment==null) Log.e("movie223","null")
                parentFragment?.startPostponedEnterTransition()
                return false
            }

            override fun onResourceReady(
                resource: Drawable?,
                model: Any?,
                target: Target<Drawable>?,
                dataSource: DataSource?,
                isFirstResource: Boolean
            ): Boolean {
                if (parentFragment==null) Log.e("movie223","null")
                parentFragment?.startPostponedEnterTransition()
                return false
            }

        }
        binding.requestListener = requestListener
        binding.imageCast.transitionName=imageUrl



    }



}