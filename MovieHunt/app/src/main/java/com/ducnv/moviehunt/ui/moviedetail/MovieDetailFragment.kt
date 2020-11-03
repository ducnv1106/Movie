package com.ducnv.moviehunt.ui.moviedetail

import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Transformation
import android.widget.CompoundButton
import android.widget.ImageView
import androidx.core.app.SharedElementCallback
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.RecyclerView
import androidx.transition.TransitionInflater
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.ducnv.moviehunt.databinding.FragmentMovieDetailBinding
import com.ducnv.moviehunt.ui.base.BaseFragment
import com.ducnv.moviehunt.R
import com.ducnv.moviehunt.data.model.Cast
import com.ducnv.moviehunt.data.model.Movie
import com.ducnv.moviehunt.ui.home.HomeActivity
import com.ducnv.moviehunt.ui.moviedetail.bottomsheet.IntroductionBottomSheet
import kotlinx.coroutines.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class MovieDetailFragment : BaseFragment<FragmentMovieDetailBinding, MovieDetailViewModel>(),
    MovieDetailListener {

    override val viewModel: MovieDetailViewModel by viewModel()

    override val getLayoutId: Int = R.layout.fragment_movie_detail

    private lateinit var castAdapter: CastAdapter

    private val args: MovieDetailFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        prepareTransitions()
        startPostponedEnterTransition()
        postponeEnterTransition()
    }

    override fun setupView() {
       binding.checkboxLike.setOnCheckedChangeListener(object :CompoundButton.OnCheckedChangeListener{
           override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
              viewModel.isLike.value=isChecked
           }

       })
        viewModel.apply {
            cast.observe(viewLifecycleOwner, Observer {
                castAdapter.submitList(it)
                loadDataSuccess()

            })
            args.movie.let {
                movie.value = it
                loadData(movieId = it.id)

            }


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
        postponeEnterTransition()
        binding.listener = this
        setupRcv()
        onRefreshListener()
    }

    private fun setupRcv() {
        castAdapter = CastAdapter()
        binding.listviewCaster.adapter = castAdapter
    }

    private fun onRefreshListener() {
        binding.refreshLayout.setOnRefreshListener {
            viewModel.apply {
                args.movie?.let {
                    refreshData(it.id)
                }
            }
        }
    }

    override fun onClickedBack() {

       findNavController().navigateUp()

    }

    override fun goToViewAllImage() {

        val dataImage= arrayListOf<String>()
        viewModel.apply {
            cast.value?.let {
                if (cast.value?.size!! > 0){
                    it.forEach { cast->
                        cast.getFullProfilePath()?.let { imageUrl ->
                            dataImage.add(imageUrl)
                        }
                    }
                    findNavController().navigate(MovieDetailFragmentDirections.toFragmentGridImageCast(
                       dataImage.toTypedArray()
                    ))
                }
            }
        }

    }

    override fun onShowBottomSheetIntroduction() {
        viewModel.movie.value?.let {
            findNavController().navigate(MovieDetailFragmentDirections.toBottomSheetIntroduction(it))
        }


    }


    private fun prepareTransitions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            sharedElementEnterTransition = TransitionInflater.from(context)
                .inflateTransition(R.transition.image_shared_element_transition)

        }


        // A similar mapping is set at the ImagePagerFragment with a setEnterSharedElementCallback.

    }


}