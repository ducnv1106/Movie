package com.ducnv.moviehunt.ui.base

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ducnv.moviehunt.R
import kotlinx.android.synthetic.main.fragment_loadmore_refresh.*

abstract class BaseLoadMoreRefreshFragment<ViewBinding:ViewDataBinding,ViewModel: BaseLoadMoreRefreshViewModel<Item>,Item> : BaseFragment<ViewBinding,ViewModel>(){

    override val getLayoutId: Int = R.layout.fragment_loadmore_refresh

    abstract val listAdapter : BaseListAdapter<Item,out ViewDataBinding>

    open fun getLayoutManager():RecyclerView.LayoutManager=LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupLoadMoreRefresh()
    }


    /**
     * setup default loadMore refresh
     */
    open fun setupLoadMoreRefresh(){
        refresh_layout?.setOnRefreshListener {
            viewModel.doRefresh()
            refresh_layout.setColorSchemeColors(Color.WHITE)
            refresh_layout.setProgressBackgroundColorSchemeColor(Color.rgb(0,165,165))

        }
        recycler_view?.layoutManager=getLayoutManager()
        recycler_view?.adapter=listAdapter
        viewModel.apply {
            itemList.observe(viewLifecycleOwner, Observer {

                // transition loadDatasucces
                postponeEnterTransition()
                listAdapter.submitList(it)
            })
            firstLoad()
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        recycler_view.adapter=null
    }
}