package com.ducnv.moviehunt.ui.home

import android.os.Parcelable
import android.service.quicksettings.Tile
import androidx.fragment.app.*
import androidx.viewpager2.adapter.FragmentStateAdapter
import org.koin.android.ext.android.get

class HomePagerAdapter(val fm: FragmentManager, val listFragment: List<Fragment>,val title:List<String>) :
    FragmentStatePagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    override fun getItem(position: Int): Fragment {
        return listFragment[position]
    }

    override fun getCount(): Int {
        return listFragment.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return title[position]
    }

    override fun saveState(): Parcelable? {
        return null
    }

}