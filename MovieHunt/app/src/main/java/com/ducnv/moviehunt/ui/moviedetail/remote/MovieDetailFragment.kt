package com.ducnv.moviehunt.ui.moviedetail.remote

import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.AnimationUtils
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.transition.TransitionInflater
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.ducnv.moviehunt.databinding.FragmentMovieDetailBinding
import com.ducnv.moviehunt.ui.base.BaseFragment
import com.ducnv.moviehunt.R
import com.ducnv.moviehunt.utils.checkedChangeListener
import com.ducnv.moviehunt.utils.setSingleClick

import org.koin.androidx.viewmodel.ext.android.viewModel

class MovieDetailFragment : BaseFragment<FragmentMovieDetailBinding, MovieDetailViewModel>(),
    MovieDetailListener {

    override val viewModel: MovieDetailViewModel by viewModel()

    override val getLayoutId: Int = R.layout.fragment_movie_detail

    private lateinit var castAdapter: CastAdapter

    private val args: MovieDetailFragmentArgs by navArgs()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        prepareTransitions()
        startPostponedEnterTransition()
        if (savedInstanceState == null) {
            postponeEnterTransition()
        }

        viewModel.apply {
            cast.observe(viewLifecycleOwner, Observer {
                castAdapter.submitList(it)
                loadDataSuccess()

            })
            args.movie.let {
                movie.value = it
                loadData(movieId = it.id.toInt())
                checkMovieLike(it.id)

            }
            following.observe(viewLifecycleOwner, Observer {
                checkRating()

            })
            rating.observe(viewLifecycleOwner, Observer {
                Log.e("movieRating", it.toString())
            })

        }

        binding.imageRequestListener = object : RequestListener<Drawable> {
            override fun onLoadFailed(
                e: GlideException?,
                model: Any?,
                target: Target<Drawable>?,
                isFirstResource: Boolean
            ): Boolean {
                startPostponedEnterTransition()
                return false
            }

            override fun onResourceReady(
                resource: Drawable?,
                model: Any?,
                target: Target<Drawable>?,
                dataSource: DataSource?,
                isFirstResource: Boolean
            ): Boolean {
                startPostponedEnterTransition()
                return false
            }
        }

        binding.listener = this
    }

    override fun setupView() {
        setupRcv()
        onRefreshListener()
        listenerCheckedChange()
        listenerClicked()
    }

    private fun setupRcv() {
        castAdapter = CastAdapter()
        binding.listviewCaster.adapter = castAdapter
    }

    private fun onRefreshListener() {
        binding.refreshLayout.setOnRefreshListener {
            viewModel.apply {
                args.movie?.let {
                    refreshData(it.id.toInt())
                }
            }
        }
    }

    override fun onClickedBack() {

        findNavController().navigateUp()

    }

    override fun goToViewAllImage() {

        val dataImage = arrayListOf<String>()
        viewModel.apply {
            cast.value?.let {
                if (cast.value?.size!! > 0) {
                    it.forEach { cast ->
                        cast.getFullProfilePath()?.let { imageUrl ->
                            dataImage.add(imageUrl)
                        }
                    }
                    findNavController().navigate(
                        MovieDetailFragmentDirections.toFragmentGridImageCast(
                            dataImage.toTypedArray()
                        )
                    )
                }
            }
        }

    }

    override fun onShowBottomSheetIntroduction() {
        viewModel.movie.value?.let {
            findNavController().navigate(
                MovieDetailFragmentDirections.toBottomSheetIntroduction(
                    it
                )
            )
        }


    }


    private fun prepareTransitions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            sharedElementEnterTransition = TransitionInflater.from(context)
                .inflateTransition(R.transition.image_shared_element_transition)
        }

    }

    override fun onStop() {
        /**
         * insert or delete data Movie to local
         */
        viewModel.apply {
            if (likeChange?.value == true) insertLikeMovie()
            else deleteLikeMovie()
        }
        super.onStop()

    }


    /**
     * listener change checked checkbox
     */
    private fun listenerCheckedChange() {
        binding.checkboxLike.checkedChangeListener {

            viewModel.likeChange.value = it


        }
    }

    // show Dialog fragment
    private fun listenerClicked() {

        viewModel.apply {
            binding.apply {
                checkboxLike.setSingleClick {
                    if (likeChange?.value == true) findNavController().navigate(
                        MovieDetailFragmentDirections.toDialogLikeMovie(
                            R.string.saved
                        )
                    )
                    else findNavController().navigate(
                        MovieDetailFragmentDirections.toDialogLikeMovie(
                            R.string.un_saved
                        )
                    )
                }

                checkboxFavorite.setSingleClick {
                    findNavController().navigate(MovieDetailFragmentDirections.toDialogFavoriteMovie())
                }

                checkboxRating.setSingleClick {
                    Log.e("movie","rating")
                    if (containerRating.isGone){
//                        containerRating.startAnimation(AnimationUtils.loadAnimation(requireContext(),R.anim.to_right_navigation))
                        containerRating.visibility=View.VISIBLE
                    }
                    else containerRating.visibility=View.GONE

                }
            }

        }

    }

}

