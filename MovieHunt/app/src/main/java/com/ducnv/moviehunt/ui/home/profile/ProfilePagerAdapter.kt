package com.ducnv.moviehunt.ui.home.profile

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class ProfilePagerAdapter(val listFragment: List<Fragment>,val fmActivity:FragmentActivity) : FragmentStateAdapter(fmActivity){
    override fun getItemCount(): Int {
        return listFragment.size
    }

    override fun createFragment(position: Int): Fragment {
       return listFragment[position]
    }


}