package com.ducnv.moviehunt.ui.base

import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.core.app.ActivityCompat.startPostponedEnterTransition
import androidx.core.view.ViewCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ducnv.moviehunt.R
import java.util.concurrent.Executors
import com.ducnv.moviehunt.BR
import com.ducnv.moviehunt.data.model.Movie
import kotlin.coroutines.coroutineContext

abstract class BaseListAdapter<Item, ViewBinding : ViewDataBinding>(callback: DiffUtil.ItemCallback<Item>) :
    ListAdapter<Item, BaseViewHolder<ViewBinding>>(
        AsyncDifferConfig.Builder<Item>(callback)
            .setBackgroundThreadExecutor(Executors.newSingleThreadExecutor()).build()
    ) {

    fun submitList(list: ArrayList<Item>) {
        super.submitList(ArrayList<Item>(list ?: listOf()))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<ViewBinding> {
        return BaseViewHolder(
            DataBindingUtil.inflate<ViewBinding>(
                LayoutInflater.from(parent.context),
                getLayoutRes(viewType),
                parent,
                false
            ).apply {
                bindFirstTime(this)
            }
        )
    }

    override fun onBindViewHolder(holder: BaseViewHolder<ViewBinding>, position: Int) {
        val item: Item? = getItem(position)
        holder.binding.setVariable(BR.item, item)
        if (item != null) {
            bindView(holder.binding, item, position)

        }
        holder.binding.executePendingBindings()

        getAnimation()?.let {
            holder.itemView.startAnimation(
                AnimationUtils.loadAnimation(
                    holder.itemView.context,
                    it!!
                )
            )
        }

    }


    open fun getAnimation(): Int? {
        return R.anim.fade_in
    }

    protected abstract fun getLayoutRes(viewType: Int): Int

    /**
     * bind first time
     * */
    protected open fun bindFirstTime(binding: ViewBinding) {}

    /**
     * bind View
     * use for set time onClickListener,something only set one
     */
    protected open fun bindView(binding: ViewBinding, item: Item, position: Int) {}
}

open class BaseViewHolder<ViewBinding : ViewDataBinding>(val binding: ViewBinding) :
    RecyclerView.ViewHolder(binding.root)