package com.ducnv.moviehunt.ui.image.girdimagecast

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
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
import com.ducnv.moviehunt.R
import com.ducnv.moviehunt.data.model.Cast
import com.ducnv.moviehunt.databinding.ItemCastBinding
import com.ducnv.moviehunt.databinding.ItemGirdImageCastBinding
import com.ducnv.moviehunt.ui.home.HomeActivity
import com.ducnv.moviehunt.utils.setSingleClick
import java.util.concurrent.Executors
import java.util.concurrent.atomic.AtomicBoolean

class GridImageCastAdapter(
    private val fm: Fragment,
    val itemClickListener: (List<String>, ImageView) -> Unit = { _: List<String>, _: ImageView -> }
) : ListAdapter<String, GridImageViewHolder>(
    AsyncDifferConfig.Builder<String>(ImageCastDifCallback())
        .setBackgroundThreadExecutor(Executors.newSingleThreadExecutor()).build()
) {

    private var requestManager: RequestManager? = null
    private var viewHolderListener: ViewHolderListener? = null

    /**
     * Constructs a new grid adapter for the given [Fragment].
     */
    init {
        requestManager = Glide.with(fm)
        viewHolderListener = ViewHolderListenerImpl(fm)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GridImageViewHolder {
        return GridImageViewHolder(
            DataBindingUtil.inflate<ItemGirdImageCastBinding>(
                LayoutInflater.from(parent.context),
                R.layout.item_gird_image_cast, parent, false
            )
                .apply {
                    binFistTime(this)
                }
        )
    }


    override fun onBindViewHolder(holder: GridImageViewHolder, position: Int) {
        val item = getItem(position)
        holder.binding.setVariable(BR.item, item)
        if (item != null) {
            bindView(binding = holder.binding, item = item, position = holder.adapterPosition)
        }
        holder.binding.executePendingBindings()
    }

    fun submitList(list: ArrayList<String>) {
        super.submitList(ArrayList<String>(list ?: listOf()))
    }

    private fun binFistTime(binding: ItemGirdImageCastBinding) {}

    private fun bindView(binding: ItemGirdImageCastBinding, item: String, position: Int) {

        // Load the image with Glide to prevent OOM error when the image drawables are very large.

        binding.cardImage.transitionName = item

        val requestOption = RequestOptions().error(R.drawable.error_image)

        val requestListener = object : RequestListener<Drawable> {
            override fun onLoadFailed(
                e: GlideException?,
                model: Any?,
                target: Target<Drawable>?,
                isFirstResource: Boolean
            ): Boolean {
                viewHolderListener!!.onLoadCompleted(binding.cardImage, position)
                return false
            }

            override fun onResourceReady(
                resource: Drawable?,
                model: Any?,
                target: Target<Drawable>?,
                dataSource: DataSource?,
                isFirstResource: Boolean
            ): Boolean {
                viewHolderListener!!.onLoadCompleted(binding.cardImage, position)
                return false
            }

        }
        requestManager
            ?.load(item)?.apply {
                diskCacheStrategy(DiskCacheStrategy.NONE)
                transition(DrawableTransitionOptions.withCrossFade())
                addListener(requestListener)
                apply(requestOption).into(binding.cardImage)
            }

        binding.apply {
            root.setSingleClick {
                item.apply {

                    // Update the position.
                    HomeActivity.currentPosition = position

                    // Exclude the clicked card from the exit transition (e.g. the card will disappear immediately
                    // instead of fading out with the rest to prevent an overlapping animation of fade and move).
                    val transitionSet = fm.exitTransition as TransitionSet
                    transitionSet.excludeTarget(binding.cardViewGridImage, true)

                    itemClickListener(currentList, binding.cardImage)

                }

            }
        }
    }

    interface ViewHolderListener {

        fun onLoadCompleted(view: ImageView?, adapterPosition: Int)

    }

    private class ViewHolderListenerImpl internal constructor(private val fragment: Fragment) :
        ViewHolderListener {
        private val enterTransitionStarted: AtomicBoolean = AtomicBoolean()
        override fun onLoadCompleted(
            view: ImageView?,
            position: Int
        ) {
            // Call startPostponedEnterTransition only when the 'selected' image loading is completed.
            if (HomeActivity.currentPosition !== position) {
                return
            }
            if (enterTransitionStarted.getAndSet(true)) {
                return
            }
            fragment.startPostponedEnterTransition()
        }
        /**
         * Handles a view click by setting the current position to the given `position` and
         * starting a [ImagePagerFragment] which displays the image at the position.
         *
         * @param view the clicked [ImageView] (the shared element view will be re-mapped at the
         * GridFragment's SharedElementCallback)
         * @param position the selected view position
         */


//            fragment.findNavController().navigate(R.id.to_view_pager_image,currentList.toTypedArray())
//            val extras= FragmentNavigatorExtras(imageView to currentList[adapterPosition].getFullProfilePath().toString())
//            val extras = FragmentNavigatorExtras(
//                imageView to imageView.transitionName
//            )
//            findNavController(fragment).navigate(MovieDetailFragmentDirections.toViewPagerImage(currentList.toTypedArray()),extras)


    }

}

class ImageCastDifCallback : DiffUtil.ItemCallback<String>() {
    override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
        return oldItem == newItem
    }

}

open class GridImageViewHolder(val binding: ItemGirdImageCastBinding) :
    RecyclerView.ViewHolder(binding.root)