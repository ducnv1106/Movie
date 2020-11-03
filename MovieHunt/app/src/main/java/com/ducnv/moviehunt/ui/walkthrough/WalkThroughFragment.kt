package com.ducnv.moviehunt.ui.walkthrough

import android.annotation.SuppressLint
import android.content.Intent
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.ducnv.moviehunt.R

import com.ducnv.moviehunt.databinding.FragmentWalkthroughBinding
import com.ducnv.moviehunt.ui.base.BaseFragment
import com.ducnv.moviehunt.ui.home.HomeActivity
import com.ducnv.moviehunt.ui.walkthrough.walkthrough1.WalkThrough1Fragment
import com.ducnv.moviehunt.ui.walkthrough.walkthrough2.WalkThrough2Fragment
import com.ducnv.moviehunt.ui.walkthrough.walkthrough3.WalkThrough3Fragment
import org.koin.androidx.viewmodel.ext.android.viewModel


class WalkThroughFragment : BaseFragment<FragmentWalkthroughBinding, WalkThroughViewModel>(),
    View.OnClickListener {

    override val viewModel: WalkThroughViewModel by viewModel()

    override val getLayoutId: Int = R.layout.fragment_walkthrough

    private lateinit var walkThroughPagerAdapter: WalkThroughPagerAdapter

    override fun setupView() {
        val listFragment = listOf<Fragment>(
            WalkThrough1Fragment(),
            WalkThrough2Fragment(),
            WalkThrough3Fragment()
        )

        walkThroughPagerAdapter = WalkThroughPagerAdapter(requireActivity(), listFragment)
        binding.viewPager.adapter = walkThroughPagerAdapter
        binding.btnNext.setOnClickListener(this)

        setupIncator()
        onPageNumberListener()


    }

    private fun setupIncator() {
        binding.indicatorView
            .setIndicatorDrawable(R.drawable.ic_movie_empty, R.drawable.ic_moive_white)
            .setIndicatorGap(30)
            .setupWithViewPager(binding.viewPager)
    }

    override fun onClick(v: View?) {

        with(binding) {
            when (v?.id) {
                R.id.btn_next -> {
                    if (viewPager.currentItem == walkThroughPagerAdapter.itemCount - 1) {
                        val sessionId=viewModel.sessionId.value
                        /**
                         * check sessionId save in local
                         * session==null goToLogin
                         * session!=null goToHome
                         */
                        if (sessionId.isNullOrBlank()) goToLogin()
                        else goToHome()
                    } else {
                        viewPager.currentItem += 1
                    }
                }

                else -> {}
            }
        }
    }

    private fun onPageNumberListener() {
        binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            @SuppressLint("UseCompatLoadingForDrawables")
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                binding.btnNext.setSupportAllCaps(false)
                if (position == walkThroughPagerAdapter.itemCount - 1) {
                    binding.btnNext.text = "Get Stared"
                    binding.btnNext.setCompoundDrawablesRelativeWithIntrinsicBounds(
                        null,
                        null,
                        null,
                        null
                    )
                } else {
                    binding.btnNext.text = "Next"
                    binding.btnNext.setCompoundDrawablesRelativeWithIntrinsicBounds(
                        null,
                        null,
                        resources.getDrawable(R.drawable.ic_arrow_right, null),
                        null
                    )
                }
            }
        })
    }

    private fun goToLogin(){
        findNavController().navigate(R.id.to_login)
    }
    private fun goToHome(){
        val intent = Intent(context, HomeActivity::class.java)
        startActivity(intent)
        activity?.finish()
    }
}