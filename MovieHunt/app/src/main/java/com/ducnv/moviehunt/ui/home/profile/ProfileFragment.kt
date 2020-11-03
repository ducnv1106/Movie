package com.ducnv.moviehunt.ui.home.profile

import android.util.Log
import android.widget.CompoundButton
import androidx.fragment.app.Fragment
import com.ducnv.moviehunt.databinding.FragmentProfileBinding
import com.ducnv.moviehunt.ui.base.BaseFragment
import com.ducnv.moviehunt.R
import com.ducnv.moviehunt.ui.favorite.FavoriteFragment
import com.ducnv.moviehunt.ui.rating.RatingFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProfileFragment : BaseFragment<FragmentProfileBinding, ProfileViewModel>(), ProfileListener {

    override val viewModel: ProfileViewModel by viewModel()

    override val getLayoutId: Int = R.layout.fragment_profile

    private lateinit var profilePagerAdapter: ProfilePagerAdapter


    override fun setupView() {
        binding.listener = this
        setUpViewPager()
      binding.radioLike.setOnCheckedChangeListener(object : CompoundButton.OnCheckedChangeListener{
          override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
              Log.e("movie",isChecked.toString())
          }

      })


    }

    private fun setUpViewPager() {

        val listFragment = listOf<Fragment>(
            FavoriteFragment(),
            RatingFragment()
        )

        profilePagerAdapter =
            ProfilePagerAdapter(listFragment = listFragment, fmActivity = requireActivity())
        binding.viewPager.adapter = profilePagerAdapter
        binding.viewPager.offscreenPageLimit = 3

        // off
        binding.viewPager.isUserInputEnabled = false

    }

    override fun onCheckedListener(id: Int) {
        when (id) {
            binding.radioLike.id -> {
                if (binding.radioLike.isChecked) return
                binding.radioLike.isChecked = true
                binding.radioFavorite.isChecked = false
                binding.radioRating.isChecked = false


            }
            binding.radioFavorite.id -> {
                if (binding.radioFavorite.isChecked) return
                binding.radioLike.isChecked = false
                binding.radioFavorite.isChecked = true
                binding.radioRating.isChecked = false


            }
            binding.radioRating.id -> {
                if (binding.radioRating.isChecked) return
                binding.radioLike.isChecked = false
                binding.radioFavorite.isChecked = false
                binding.radioRating.isChecked = true


            }

        }
    }


}