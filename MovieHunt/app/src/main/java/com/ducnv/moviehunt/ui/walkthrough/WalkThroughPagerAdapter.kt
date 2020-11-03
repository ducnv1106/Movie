package com.ducnv.moviehunt.ui.walkthrough

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.fragment.app.ListFragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class WalkThroughPagerAdapter(private val fm:FragmentActivity, private val listFragment: List<Fragment>) : FragmentStateAdapter(fm){

    override fun getItemCount(): Int {
        return listFragment.size
    }

    override fun createFragment(position: Int): Fragment {
       return listFragment[position]
    }

}