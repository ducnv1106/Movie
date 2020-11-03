package com.ducnv.moviehunt.ui.home

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Build

import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import androidx.navigation.findNavController
import androidx.transition.Explode
import com.ducnv.moviehunt.R
import com.ducnv.moviehunt.databinding.ActivityHomeBinding
import com.ducnv.moviehunt.ui.base.BaseActivity

class HomeActivity : BaseActivity<ActivityHomeBinding>() {

    companion object {
        var currentPosition: Int = 0
        const val KEY_CURRENT_POSITION = "ducnv1106"
    }

    override val getLayoutId: Int = R.layout.activity_home

    @SuppressLint("ResourceAsColor")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


            // transparent statusbar
        if (Build.VERSION.SDK_INT >= 19 && Build.VERSION.SDK_INT < 21) {
            setWindowFlag(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, true)
        }
        if (Build.VERSION.SDK_INT >= 19) {
            window.decorView.systemUiVisibility =
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        }
        if (Build.VERSION.SDK_INT >= 21) {
            setWindowFlag(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, false)
            //transparent statusbar
            window.statusBarColor = Color.TRANSPARENT
            //transparent navigation bar
            window.navigationBarColor=Color.TRANSPARENT
        }

        if (savedInstanceState != null) {
            currentPosition = savedInstanceState.getInt(KEY_CURRENT_POSITION,0)
        }
    }

    private fun setWindowFlag(bits: Int, on: Boolean) {
        val win = window
        val winParams = win.attributes
        if (on) {
            winParams.flags = winParams.flags or bits
        } else {
            winParams.flags = winParams.flags and bits.inv()
        }
        win.attributes = winParams
    }


    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(KEY_CURRENT_POSITION, currentPosition)
    }


}