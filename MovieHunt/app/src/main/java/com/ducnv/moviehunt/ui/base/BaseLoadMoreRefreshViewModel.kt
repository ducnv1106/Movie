package com.ducnv.moviehunt.ui.base

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.ducnv.moviehunt.constants.Constants
import com.ducnv.moviehunt.ui.widgets.EndlessRecyclerOnScrollListener
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

abstract class BaseLoadMoreRefreshViewModel<Item> : BaseViewModel(){

    // refresh
    val isRefreshing = MutableLiveData<Boolean>().apply { value=false }

    //load more
    val isLoadMore = MutableLiveData<Boolean>().apply { value=false }

    //curent page
    private val currentPage = MutableLiveData<Int>().apply { value=getPreFirstPage() }

    // last page
    private val isLastPage=MutableLiveData<Boolean>().apply { value=false}

    //scroll listener for recycler view
    val onScrollListener=object : EndlessRecyclerOnScrollListener(getLoadMoreThreshold()){
        override fun onLoadMore() {
            doLoadMore()
        }
    }
     val isSearchData=MutableLiveData<Boolean>().apply { value=true }

    //item list
    val itemList=MutableLiveData<ArrayList<Item>>()

    // empty list
    val isEmptyList= MutableLiveData<Boolean>().apply { value=false }

    /**
     * load data
     */

    abstract fun loadData(page:Int)

    /**
     * load data search
     */
    open fun loadDataSearchView(page: Int){}

    /**
     * check first time load data
     */
    private fun isFirst()=currentPage.value==getPreFirstPage() && itemList.value?.isEmpty() ?: true

    /**
     * first load
     */
    fun firstLoad(){
        if (isFirst()){
            showLoading()
            loadData(getFirstPage())
            loadDataSearchView(getPreFirstPage())
        }
    }

    fun doRefresh(){
        if(isLoading.value==true || isRefreshing.value==true) return
        isRefreshing.value=true
        refreshData()
    }
    /**
     * load first page
     */
    protected fun refreshData(){
        loadData(getFirstPage())
    }

    fun doLoadMore(){
        if (isLoading.value==true || isRefreshing.value==true || isLoadMore.value == true || isLastPage.value==true) {
            return
        }
        isLoadMore.value=true
        loadMore()
    }

    /**
     * load next page
     */
    protected fun loadMore(){
        loadData(currentPage.value?.plus(1) ?: getFirstPage())
    }

    /**
     * override if first page is not 1
     */
    open fun getFirstPage()= Constants.DEFAULT_FIRST_PAGE

    private fun getPreFirstPage()=getFirstPage()-1

    /**
     * override if need change number visible threshold
     */
    open fun getLoadMoreThreshold()= Constants.DEFAULT_NUM_VISIBLE_THRESHOLD
    /**
     * override if need change number item per page
     */
    open fun getNumberItemPerPage()= Constants.DEFAULT_ITEM_PER_PAGE

    /**
     * reset load more
     */
    fun resetLoadMore(){
        onScrollListener.resetOnLoadMore()
        isLastPage.value=false
    }
    /**
     * handler load success
     */
    suspend fun onLoadSuccess(page:Int,items:List<Item>?){
        withContext(Dispatchers.Main){
            // load success then update current page
            currentPage.value=page
            //case load first page then clear data from listItem
            if (currentPage.value==getFirstPage()) itemList.value?.clear()
            //case refresh then reset load more
            if (isRefreshing.value==true) resetLoadMore()

            //add new data to listener
            val newList=itemList.value ?: ArrayList()
            newList.addAll(items ?: listOf())
            itemList.value=newList

            //check last page
            isLastPage.value=items?.size ?: 0 < getNumberItemPerPage()

            // reset load
            hideLoading()
            isRefreshing.value=false
            isLoadMore.value=false
            checkEmptyList()

        }
    }

    override suspend fun onError(throwable: Throwable) {
        withContext(Dispatchers.Main){
            super.onError(throwable)
            onScrollListener.isLoading=false

            //reset load
            isRefreshing.value=false
            isLoadMore.value=false

            //check empty list
            checkEmptyList()
        }
    }
    /**
     * check list is empty
     */
    private fun checkEmptyList(){
        isEmptyList.value=itemList.value?.isEmpty() ?: true
    }
}