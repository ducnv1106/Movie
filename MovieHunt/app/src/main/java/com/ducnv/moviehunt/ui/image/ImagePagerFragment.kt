package com.ducnv.moviehunt.ui.image

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.SharedElementCallback
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.findFragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.transition.TransitionInflater
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.ducnv.moviehunt.BR
import com.ducnv.moviehunt.R
import com.ducnv.moviehunt.databinding.FragmentImagePagerBinding
import com.ducnv.moviehunt.ui.base.BaseFragment
import com.ducnv.moviehunt.ui.home.HomeActivity
import com.ducnv.moviehunt.utils.findFragmentAtPosition
import kotlinx.coroutines.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class ImagePagerFragment : BaseFragment<FragmentImagePagerBinding, ImagePagerViewModel>() {

    override val viewModel: ImagePagerViewModel by viewModel()

    override val getLayoutId: Int = R.layout.fragment_image_pager

    private lateinit var adapter: ImagePagerAdapter

    private val ioScope = CoroutineScope(Dispatchers.IO)


    private var listenerImagePagerListener: ImagePagerListener = object : ImagePagerListener {
        override fun onBackPress() {
                findNavController().navigateUp()
            }

    }

    private val args: ImagePagerFragmentArgs by navArgs()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel.apply {
            args.listImage?.let {
                this.listCast.value = it

            }
        }
        binding.listener=listenerImagePagerListener
        prepareSharedElementTransition()
        startPostponedEnterTransition()
        if (savedInstanceState == null) {
            postponeEnterTransition()
        }


    }


    private fun onPageChangeListener() {
        binding.viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {

            }

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {

            }

            override fun onPageSelected(position: Int) {
                HomeActivity.currentPosition = position
            }

        })
    }

    private fun setUpViewPager() {
        viewModel.apply {
            listCast.observe(viewLifecycleOwner, Observer {
                val listFragment= arrayListOf<Fragment>().apply {
                    it.forEach { imageUrl ->
                         this.add(ImageFragment.newInstance(imageUrl))
                    }
                }
                adapter = ImagePagerAdapter(this@ImagePagerFragment, listFragment)
                binding.viewPager.adapter = adapter
                binding.viewPager.currentItem = HomeActivity.currentPosition



            })
        }

    }

    override fun setupView() {
        setUpViewPager()
        onPageChangeListener()
    }

    private fun prepareSharedElementTransition() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            sharedElementEnterTransition = TransitionInflater.from(context)
                .inflateTransition(R.transition.image_shared_element_transition)

        }

        // A similar mapping is set at the GridFragment with a setExitSharedElementCallback.

        setEnterSharedElementCallback(
            object : SharedElementCallback() {
                override fun onMapSharedElements(
                    names: List<String>,
                    sharedElements: MutableMap<String, View>
                ) {
                    // Locate the image view at the primary fragment (the ImageFragment that is currently
                    // visible). To locate the fragment, call instantiateItem with the selection position.
                    // At this stage, the method will simply return the fragment at the position and will
                    // not create a new one.
                    val currentFragment =
                        binding.viewPager.adapter?.instantiateItem(binding.viewPager,HomeActivity.currentPosition) as Fragment

                    val view = currentFragment.view
                    // Map the first shared element name to the child ImageView.
                    if (view==null)  Log.e("movie2","null")
                    if (view != null) {
                        sharedElements[names[0]] =
                            view.findViewById(R.id.image_cast)

                    }

                }
            })
    }



}