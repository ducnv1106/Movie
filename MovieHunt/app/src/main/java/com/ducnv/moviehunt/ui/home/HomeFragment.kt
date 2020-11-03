package com.ducnv.moviehunt.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.ducnv.moviehunt.databinding.FragmentHomeBinding
import com.ducnv.moviehunt.ui.base.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel
import com.ducnv.moviehunt.R
import com.ducnv.moviehunt.ui.home.like.LikeFragment
import com.ducnv.moviehunt.ui.home.movie.MovieFragment
import com.ducnv.moviehunt.ui.home.tv.TvFragment
import com.ducnv.moviehunt.ui.home.profile.ProfileFragment
import com.ducnv.moviehunt.ui.widgets.ViewPagerFixed

class HomeFragment : BaseFragment<FragmentHomeBinding, HomeViewModel>(),
    ViewPagerFixed.OnSwipeListener {

    override val viewModel: HomeViewModel by viewModel()

    override val getLayoutId: Int = R.layout.fragment_home

    private lateinit var homePagerAdapter: HomePagerAdapter

    override fun setupView() {


        setupBottomNavigator()
        onListenerBottomNavigator()
        onListenerViewPager()
    }
    private fun setupViewPager(){
        val listFragment = listOf<Fragment>(
            MovieFragment(),
            TvFragment(),
            LikeFragment(),
            ProfileFragment()
        )

        val titles = listOf<String>(
            "MOVIE",
            "TV",
            "FAVORITE",
            "PROFILE"
        )

        homePagerAdapter = HomePagerAdapter(
            fm = requireActivity().supportFragmentManager,
            listFragment = listFragment,
            title = titles
        )

        binding.viewPager.adapter = homePagerAdapter
        binding.viewPager.offscreenPageLimit=3
        binding.viewPager.setOnSwipeListener(this)
    }



    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupViewPager()
    }

    override fun onHideBottomNavigator() {
        if (binding.bottomNavigation.isVisible && binding.viewPager.currentItem==0){
            binding.bottomNavigation.startAnimation(
                AnimationUtils.loadAnimation(context, R.anim.hide_navigator)
            )
        }
        binding.bottomNavigation.visibility=View.GONE
    }

    override fun onShowBottomNavigator() {
        if (!binding.bottomNavigation.isVisible && binding.viewPager.currentItem==0){
            binding.bottomNavigation.startAnimation(
                AnimationUtils.loadAnimation(context,R.anim.show_navigator)
            )
        }
        binding.bottomNavigation.visibility=View.VISIBLE
    }
    private fun setupBottomNavigator(){
        binding.bottomNavigation.itemIconTintList=null
//        binding.bottomNavigation.itemTextColor=null
    }
    private fun onListenerViewPager(){
        binding.viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener{
            override fun onPageScrollStateChanged(state: Int) {

            }

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
            }

            override fun onPageSelected(position: Int) {
                binding.bottomNavigation.menu.getItem(position).isChecked=true
            }

        })
    }
    private fun onListenerBottomNavigator(){
        binding.bottomNavigation.setOnNavigationItemSelectedListener {
            when(it.itemId){

                R.id.home -> binding.viewPager.currentItem = 0

                R.id.tv-> binding.viewPager.currentItem=1

                R.id.like-> binding.viewPager.currentItem=2

                R.id.profile->binding.viewPager.currentItem=3

            }
            true
        }

    }


}