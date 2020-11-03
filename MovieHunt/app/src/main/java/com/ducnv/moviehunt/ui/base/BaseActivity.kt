package com.ducnv.moviehunt.ui.base

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

abstract class BaseActivity<ViewBinding : ViewDataBinding> : AppCompatActivity() {

    protected lateinit var binding: ViewBinding

    @get:LayoutRes
    protected abstract val getLayoutId: Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, getLayoutId)
        setupView()
    }

    open fun setupView() {}

}