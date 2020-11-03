//package com.ducnv.moviehunt.ui.base
//
//import android.view.LayoutInflater
//import android.view.ViewGroup
//import android.view.animation.AnimationUtils
//import androidx.databinding.DataBindingUtil
//import androidx.databinding.ViewDataBinding
//import androidx.paging.PagedListAdapter
//import androidx.recyclerview.widget.DiffUtil
//import com.ducnv.moviehunt.BR
//import com.ducnv.moviehunt.R
//
//abstract class BasePagedListAdapter<Item, ViewBinding : ViewDataBinding>(callback: DiffUtil.ItemCallback<Item>) :
//    PagedListAdapter<Item, BaseViewHolder<ViewBinding>>(callback) {
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<ViewBinding> {
//        return BaseViewHolder(DataBindingUtil.inflate<ViewBinding>(LayoutInflater.from(parent.context),getLayoutRes(viewType),parent,false).apply {
//            bindFirstTime(this)
//        })
//    }
//
//    override fun onBindViewHolder(holder: BaseViewHolder<ViewBinding>, position: Int) {
//        val item:Item?=getItem(position)
//        holder.binding.setVariable(BR.item,item)
//        if (item!=null){
//            bindView(holder.binding,item,position)
//        }
//        holder.binding.executePendingBindings()
//        holder.itemView.startAnimation(
//            AnimationUtils.loadAnimation(holder.itemView.context,R.anim.fade_in)
//        )
//    }
//
//    /**
//     * get layoutRes based on view type
//     */
//    protected abstract fun getLayoutRes(viewType: Int):Int
//
//    /**
//     * bind first time
//     * use for set item onClickListener
//     */
//    protected open fun bindFirstTime(binding: ViewBinding){}
//
//    /**
//     * bind view
//     */
//    protected open fun bindView(binding: ViewBinding,item: Item,position: Int){}
//}