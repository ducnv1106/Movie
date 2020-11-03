package com.ducnv.moviehunt.utils

import android.graphics.drawable.Drawable
import android.os.SystemClock
import android.view.View
import android.widget.CheckBox
import android.widget.CompoundButton
import android.widget.ImageView
import android.widget.RatingBar
import androidx.appcompat.widget.AppCompatRadioButton
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.ducnv.moviehunt.R
import com.ducnv.moviehunt.constants.Constants
import kotlinx.android.synthetic.main.fragment_profile.view.*

@BindingAdapter("onSingleClick")
fun View.setSingleClick(execution: () -> Unit) {
    setOnClickListener(object : View.OnClickListener {
        var lastClickTime: Long = 0
        override fun onClick(p0: View?) {
            if (SystemClock.elapsedRealtime() - lastClickTime < Constants.THRESHOLD_CLICK_TIME) {
                return
            }
            lastClickTime = SystemClock.elapsedRealtime()
            execution.invoke()
        }
    })
}

@BindingAdapter(value = ["image", "requestListener"], requireAll = false)
fun ImageView.loadImage(
    imageUrl: String? = null,
    requestListener: RequestListener<Drawable>? = null
) {
    val requestOption = RequestOptions().error(R.drawable.error_image)
    Glide.with(context).load(imageUrl).apply {
        diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
        transition(DrawableTransitionOptions.withCrossFade())
        addListener(requestListener)
        apply(requestOption).into(this@loadImage)
    }
}
@BindingAdapter("transitionName")
fun View.setTransitionName(string: String?){
    if (string.isNullOrBlank()) this.transitionName=R.drawable.error_image.toString()
    else this.transitionName=string
}

@BindingAdapter("enableRefreshing")
fun SwipeRefreshLayout.enableRefresh(enable: Boolean?) {
    isEnabled = enable == true
}

@BindingAdapter("isChecked")
fun CheckBox.isChecked(checked:Boolean){

    isChecked=checked==true
}
@BindingAdapter("isRefreshing")
fun SwipeRefreshLayout.customRefreshing(refreshing: Boolean?) {
    isRefreshing = refreshing == true
}

@BindingAdapter("onScrollListener")
fun RecyclerView.customScrollListener(listener: RecyclerView.OnScrollListener) {
    if (listener != null) {
        addOnScrollListener(listener)

    }
}

@BindingAdapter("isVisible")
fun View.visible(visible: Boolean) {
    if (visible) {
        this.visibility = View.VISIBLE
    } else {
        this.visibility = View.GONE
    }
}

@BindingAdapter("rating")
fun RatingBar.rating(rating: Double?) {
    this.rating = rating?.toFloat()?.div(2) ?: 0.0f
}

fun AppCompatRadioButton.onChangeCheckedRadio(listener: CompoundButton.OnCheckedChangeListener) {

    when (this.id) {
        radio_like.id -> {
            if (radio_like.isChecked) return
            radio_like.isChecked = true
            radio_favorite.isChecked = false
            radio_rating.isChecked = false


        }
        radio_favorite.id -> {
            if (radio_favorite.isChecked) return
            radio_like.isChecked = false
            radio_favorite.isChecked = true
            radio_rating.isChecked = false


        }
        radio_rating.id -> {
            if (radio_rating.isChecked) return
            radio_like.isChecked = false
            radio_favorite.isChecked = false
            radio_rating.isChecked = true


        }

    }
}