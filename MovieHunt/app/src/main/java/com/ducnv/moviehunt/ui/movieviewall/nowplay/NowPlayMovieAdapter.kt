package com.ducnv.moviehunt.ui.movieviewall.nowplay

import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DiffUtil
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.ducnv.moviehunt.R
import com.ducnv.moviehunt.data.model.Movie
import com.ducnv.moviehunt.databinding.ItemMovieNowPlayBinding
import com.ducnv.moviehunt.ui.base.BaseListAdapter
import com.ducnv.moviehunt.utils.setSingleClick

class NowPlayMovieAdapter(private val fm:Fragment, val itemClickListener: (Movie,ImageView) -> Unit={ _: Movie, _: ImageView -> }) :
    BaseListAdapter<Movie, ItemMovieNowPlayBinding>(object : DiffUtil.ItemCallback<Movie>() {
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem == newItem
        }
    }) {
    override fun getLayoutRes(viewType: Int): Int {
        return R.layout.item_movie_now_play
    }

    override fun getAnimation(): Int? =null

    override fun bindView(binding: ItemMovieNowPlayBinding, item: Movie, position: Int) {

        binding.requestListener=object : RequestListener<Drawable>{
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
//            val image=binding.imagePoster
//            image.transitionName=item.getPosterPath()
            root.setSingleClick {
                item.apply {
                    itemClickListener(this,binding.imagePoster)
                }
            }
        }
    }

}