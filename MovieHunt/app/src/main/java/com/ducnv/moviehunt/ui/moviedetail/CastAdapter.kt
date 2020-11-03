package com.ducnv.moviehunt.ui.moviedetail


import androidx.recyclerview.widget.DiffUtil
import com.ducnv.moviehunt.R
import com.ducnv.moviehunt.data.model.Cast
import com.ducnv.moviehunt.databinding.ItemCastBinding
import com.ducnv.moviehunt.ui.base.BaseListAdapter


class CastAdapter : BaseListAdapter<Cast,ItemCastBinding>(CastDiffCallBack()){

    override fun getLayoutRes(viewType: Int): Int {
        return R.layout.item_cast
    }

}
class CastDiffCallBack: DiffUtil.ItemCallback<Cast>(){
    override fun areItemsTheSame(oldItem: Cast, newItem: Cast): Boolean {
        return oldItem.id==newItem.id
    }

    override fun areContentsTheSame(oldItem: Cast, newItem: Cast): Boolean {
        return oldItem==newItem
    }

}

