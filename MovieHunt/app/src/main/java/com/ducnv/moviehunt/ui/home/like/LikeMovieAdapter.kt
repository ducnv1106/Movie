package com.ducnv.moviehunt.ui.home.like

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.transition.TransitionSet
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.ducnv.moviehunt.BR
import com.ducnv.moviehunt.data.model.Movie
import com.ducnv.moviehunt.databinding.ItemMovieLikeFragmentBinding
import com.ducnv.moviehunt.ui.base.BaseListAdapter
import com.ducnv.moviehunt.R
import com.ducnv.moviehunt.databinding.ItemGirdImageCastBinding
import com.ducnv.moviehunt.ui.home.HomeActivity
import com.ducnv.moviehunt.ui.home.HomeFragmentDirections
import com.ducnv.moviehunt.ui.image.girdimagecast.GridImageCastAdapter
import com.ducnv.moviehunt.utils.setSingleClick
import java.util.concurrent.Executors
import java.util.concurrent.atomic.AtomicBoolean

class LikeMovieAdapter(private val fm: Fragment) :
    BaseListAdapter<Movie, ItemMovieLikeFragmentBinding>(LikeMovieDiffused()) {


    override fun getLayoutRes(viewType: Int): Int = R.layout.item_movie_like_fragment

    override fun bindView(binding: ItemMovieLikeFragmentBinding, item: Movie, position: Int) {

        binding.requestListener = object : RequestListener<Drawable> {
            override fun onLoadFailed(
                e: GlideException?,
                model: Any?,
                target: Target<Drawable>?,
                isFirstResource: Boolean
            ): Boolean {
                fm.startPostponedEnterTransition()
                return false
            }

            override fun onResourceReady(
                resource: Drawable?,
                model: Any?,
                target: Target<Drawable>?,
                dataSource: DataSource?,
                isFirstResource: Boolean
            ): Boolean {
                fm.startPostponedEnterTransition()
                return false
            }

        }
        binding.executePendingBindings()
        binding.apply {
            root.setSingleClick {
                item.apply {
                    HomeActivity.currentPositionItemLikeMovie = position

                    val extras = FragmentNavigatorExtras(
                        binding.imagePosterLikeMovieFragment to binding.imagePosterLikeMovieFragment.transitionName,
                        binding.tvTitle to binding.tvTitle.transitionName,
                        binding.rating to binding.rating.transitionName,
                        binding.tvReviews to binding.tvReviews.transitionName
                    )
                    fm.findNavController()
                        .navigate(HomeFragmentDirections.toMovieDetail(this), extras)
                }
            }
        }
    }

}

//class LikeMovieAdapter(
//    private val fm: Fragment,
//    val itemClickListener: (List<Movie>, ImageView) -> Unit = { _: List<Movie>, _: ImageView -> }
//) : ListAdapter<Movie, LikeMovieViewHolder>(
//    AsyncDifferConfig.Builder<Movie>(LikeMovieDiffused())
//        .setBackgroundThreadExecutor(Executors.newSingleThreadExecutor()).build()
//) {
//
//    private var requestManager: RequestManager? = null
//    private var viewHolderListener: ViewHolderListener? = null
//
//    /**
//     * Constructs a new grid adapter for the given [Fragment].
//     */
//    init {
//        requestManager = Glide.with(fm)
//        viewHolderListener = ViewHolderListenerImpl(fm)
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LikeMovieViewHolder {
//        return LikeMovieViewHolder(
//            DataBindingUtil.inflate<ItemMovieLikeFragmentBinding>(
//                LayoutInflater.from(parent.context),
//                R.layout.item_movie_like_fragment, parent, false
//            )
//                .apply {
//                    binFistTime(this)
//                }
//        )
//    }
//
//
//    override fun onBindViewHolder(holder: LikeMovieViewHolder, position: Int) {
//        val item = getItem(position)
//        holder.binding.setVariable(BR.item, item)
//        if (item != null) {
//            bindView(binding = holder.binding, item = item, position = holder.adapterPosition)
//        }
//        holder.binding.executePendingBindings()
//    }
//
//    fun submitList(list: ArrayList<Movie>) {
//        super.submitList(ArrayList<Movie>(list ?: listOf()))
//    }
//
//    private fun binFistTime(binding: ItemMovieLikeFragmentBinding) {}
//
//    private fun bindView(binding: ItemMovieLikeFragmentBinding, item: Movie, position: Int) {
//
//        // Load the image with Glide to prevent OOM error when the image drawables are very large.
//
//        val requestOption = RequestOptions().error(R.drawable.error_image)
//
//        val requestListener = object : RequestListener<Drawable> {
//            override fun onLoadFailed(
//                e: GlideException?,
//                model: Any?,
//                target: Target<Drawable>?,
//                isFirstResource: Boolean
//            ): Boolean {
//                viewHolderListener!!.onLoadCompleted(binding.imagePosterLikeMovieFragment, position)
//                return false
//            }
//
//            override fun onResourceReady(
//                resource: Drawable?,
//                model: Any?,
//                target: Target<Drawable>?,
//                dataSource: DataSource?,
//                isFirstResource: Boolean
//            ): Boolean {
//                viewHolderListener!!.onLoadCompleted(binding.imagePosterLikeMovieFragment, position)
//                return false
//            }
//
//        }
//        requestManager
//            ?.load(item)?.apply {
//                diskCacheStrategy(DiskCacheStrategy.NONE)
//                transition(DrawableTransitionOptions.withCrossFade())
//                addListener(requestListener)
//                apply(requestOption).into(binding.imagePosterLikeMovieFragment)
//            }
//
//        binding.apply {
//            root.setSingleClick {
//                item.apply {
//
//                    // Update the position.
//                    HomeActivity.currentPosition = position
//
//                    // Exclude the clicked card from the exit transition (e.g. the card will disappear immediately
//                    // instead of fading out with the rest to prevent an overlapping animation of fade and move).
////                    val transitionSet = fm.exitTransition as TransitionSet
////                    transitionSet.excludeTarget(binding.cardViewLikeMovie, true)
//
////                    itemClickListener(currentList, binding.imagePosterLikeMovieFragment)
//
//                    val extras = FragmentNavigatorExtras(
//                        binding.imagePosterLikeMovieFragment to binding.imagePosterLikeMovieFragment.transitionName,
//                        binding.tvTitle to binding.tvTitle.transitionName,
//                        binding.rating to binding.rating.transitionName,
//                        binding.tvReviews to binding.tvReviews.transitionName
//                    )
//                    fm.findNavController()
//                        .navigate(HomeFragmentDirections.toMovieDetail(this), extras)
//
//                }
//
//            }
//        }
//    }
//
//    interface ViewHolderListener {
//
//        fun onLoadCompleted(view: ImageView?, adapterPosition: Int)
//
//    }
//
//    private class ViewHolderListenerImpl internal constructor(private val fragment: Fragment) :
//        ViewHolderListener {
//        private val enterTransitionStarted: AtomicBoolean = AtomicBoolean()
//        override fun onLoadCompleted(
//            view: ImageView?,
//            position: Int
//        ) {
//            // Call startPostponedEnterTransition only when the 'selected' image loading is completed.
//            if (HomeActivity.currentPosition !== position) {
//                return
//            }
//            if (enterTransitionStarted.getAndSet(true)) {
//                return
//            }
//            fragment.startPostponedEnterTransition()
//        }
//        /**
//         * Handles a view click by setting the current position to the given `position` and
//         * starting a [ImagePagerFragment] which displays the image at the position.
//         *
//         * @param view the clicked [ImageView] (the shared element view will be re-mapped at the
//         * GridFragment's SharedElementCallback)
//         * @param position the selected view position
//         */
//
//
////            fragment.findNavController().navigate(R.id.to_view_pager_image,currentList.toTypedArray())
////            val extras= FragmentNavigatorExtras(imageView to currentList[adapterPosition].getFullProfilePath().toString())
////            val extras = FragmentNavigatorExtras(
////                imageView to imageView.transitionName
////            )
////            findNavController(fragment).navigate(MovieDetailFragmentDirections.toViewPagerImage(currentList.toTypedArray()),extras)
//
//
//    }
//
//}

//class ImageCastDifCallback : DiffUtil.ItemCallback<String>() {
//    override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
//        return oldItem == newItem
//    }
//
//    override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
//        return oldItem == newItem
//    }
//
//}
//
//open class LikeMovieViewHolder(val binding: ItemMovieLikeFragmentBinding) :
//    RecyclerView.ViewHolder(binding.root)
class LikeMovieDiffused : DiffUtil.ItemCallback<Movie>() {
    override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem == newItem
    }

}