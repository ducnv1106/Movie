package com.ducnv.moviehunt.ui.splash

import android.os.Build
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.ducnv.moviehunt.databinding.FragmentSplashBinding
import com.ducnv.moviehunt.ui.base.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel
import com.ducnv.moviehunt.R
import kotlinx.coroutines.*


class SplashFragment : BaseFragment<FragmentSplashBinding,SplashViewModel>(){

    override val viewModel: SplashViewModel by viewModel()

    override val getLayoutId: Int = R.layout.fragment_splash
    private val activityScope= CoroutineScope(Dispatchers.Main)

    override fun onStart() {
        super.onStart()
        GlobalScope.launch {
            delay(1000)
            navigateOther()
        }
    }

    private fun setupNoStatusBar(){
        if(Build.VERSION.SDK_INT<16){
//            getwindowwindow.setFlags(
//                WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN)

        }
    }
    private fun navigateOther(){
//      findNavController().navigate(
//          R.id.to_walkthrough
//      )
      findNavController().navigate(R.id.to_walkthrough)

    }

    override fun onStop() {
        activityScope.cancel()
        super.onStop()
    }

}