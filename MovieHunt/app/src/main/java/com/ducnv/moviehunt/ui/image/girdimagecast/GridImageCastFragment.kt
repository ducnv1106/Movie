package com.ducnv.moviehunt.ui.image.girdimagecast

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.app.SharedElementCallback
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.RecyclerView
import androidx.transition.TransitionInflater
import com.ducnv.moviehunt.databinding.FragmentGridImageCastBinding
import com.ducnv.moviehunt.ui.base.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel
import com.ducnv.moviehunt.R
import com.ducnv.moviehunt.data.model.Cast
import com.ducnv.moviehunt.ui.home.HomeActivity
import com.ducnv.moviehunt.utils.setSingleClick

class GridImageCastFragment : BaseFragment<FragmentGridImageCastBinding, GridImageCastViewModel>(){

    override val viewModel: GridImageCastViewModel by viewModel()

    override val getLayoutId: Int = R.layout.fragment_grid_image_cast

    private val args : GridImageCastFragmentArgs by navArgs()

    private lateinit var adapter : GridImageCastAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        scrollToPosition()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        prepareTransitions()
        postponeEnterTransition()
        return super.onCreateView(inflater, container, savedInstanceState)
    }
    override fun setupView() {
        setUpAdapter()

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        args.let {
            adapter.submitList(it.listImage.toCollection(ArrayList()))
        }

    }

    private fun setUpAdapter(){
        adapter= GridImageCastAdapter(this,itemClickListener = { list: List<String>, imageView: ImageView -> goToImagePager(list,imageView) })
        binding.listImage.adapter=adapter



    }

    private fun goToImagePager(list: List<String>,imageView:ImageView){

        val extras= FragmentNavigatorExtras(imageView to imageView.transitionName)

        findNavController().navigate(GridImageCastFragmentDirections.toFragmentImagePager(list.toTypedArray()),extras)
    }

    private fun prepareTransitions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            exitTransition = TransitionInflater.from(context)
                .inflateTransition(R.transition.grid_exit_transition)

        }

        // A similar mapping is set at the ImagePagerFragment with a setEnterSharedElementCallback.
        setExitSharedElementCallback(
            object : SharedElementCallback() {
                override fun onMapSharedElements(
                    names: List<String>,
                    sharedElements: MutableMap<String, View>
                ) {
                    // Locate the ViewHolder for the clicked position.
                    val selectedViewHolder: RecyclerView.ViewHolder = binding.listImage
                        .findViewHolderForAdapterPosition(HomeActivity.currentPosition)
                        ?: return

                    // Map the first shared element name to the child ImageView.
                    selectedViewHolder?.itemView?.findViewById<ImageView>(R.id.card_image)?.let {
                        sharedElements[names[0]] = it

                    }

                }
            })
    }

    private fun scrollToPosition() {
        binding.listImage.addOnLayoutChangeListener(object : View.OnLayoutChangeListener {
            override fun onLayoutChange(
                v: View,
                left: Int,
                top: Int,
                right: Int,
                bottom: Int,
                oldLeft: Int,
                oldTop: Int,
                oldRight: Int,
                oldBottom: Int
            ) {
                binding.listImage.removeOnLayoutChangeListener(this)
                val layoutManager: RecyclerView.LayoutManager? = binding.listImage.layoutManager
                val viewAtPosition =
                    layoutManager?.findViewByPosition(HomeActivity.currentPosition)
                // Scroll to position if the view for the current position is null (not currently part of
                // layout manager children), or it's not completely visible.
                // Scroll to position if the view for the current position is null (not currently part of
                // layout manager children), or it's not completely visible.
                if (
                    viewAtPosition == null || layoutManager
                        .isViewPartiallyVisible(viewAtPosition, false, true)
                ) {

                    binding.listImage.post {
                        layoutManager!!.scrollToPosition(
                            HomeActivity.currentPosition
                        )
                    }
                }
            }
        })
    }


}