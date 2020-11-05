package com.ducnv.moviehunt.ui.base

import android.os.Bundle
import android.os.Message
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.*
import androidx.navigation.fragment.findNavController
import com.ducnv.moviehunt.BR
import com.ducnv.moviehunt.R
import com.kaopiz.kprogresshud.KProgressHUD
import com.varunjohn1990.iosdialogs4android.IOSDialog


abstract class BaseFragment<ViewBinding : ViewDataBinding, VieModel : BaseViewModel> : Fragment() {

    protected lateinit var binding: ViewBinding

    protected abstract val viewModel: VieModel

    @get:LayoutRes
    protected abstract val getLayoutId: Int

    /**
     * single show loading dialog
     */
    protected var loadingDialog: KProgressHUD? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(LayoutInflater.from(context), getLayoutId, container, false)
        binding.apply {
            setVariable(BR.viewModel, viewModel)
            root.isClickable = true
            binding.executePendingBindings()
        }
        setupView()
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel.apply {
            isLoading.observe(viewLifecycleOwner, Observer {
                handleLoading(it)

            })
            errorMessage.observe(viewLifecycleOwner, Observer {
                handlerErrorMessage(it)
            })
            noInternetConnectionEvent.observe(viewLifecycleOwner, Observer {
                handlerErrorMessage(getString(R.string.no_internet_connection))
            })
            connectTimeoutEvent.observe(viewLifecycleOwner, Observer {
                handlerErrorMessage(getString(R.string.connect_timeout))
            })
            forceUpdateAppEvent.observe(viewLifecycleOwner, Observer {
                handlerErrorMessage(getString(R.string.force_update_app))
            })
            serverMaintainEvent.observe(viewLifecycleOwner, Observer {
                handlerErrorMessage(getString(R.string.server_maintain_message))
            })
            unknownErrorEvent.observe(viewLifecycleOwner, Observer {
                handlerErrorMessage(getString(R.string.unknown_error))
            })
            unauThorizedEvent.observe(viewLifecycleOwner, Observer {
                handlerErrorMessage(getString(R.string.unauThorized_error))
            })
        }

    }

    /**
     * override this if not use loading dialog (example progress bar)
     */
    open fun handleLoading(isLoading: Boolean) {
        if (isLoading) showLoadingDialog()
        else dismissLoadingDialog()
    }

    private fun handlerErrorMessage(message: String?) {
        if (message.isNullOrBlank()) return
        dismissLoadingDialog()
        showDialog(message)

    }


    fun navigateUp() {
        findNavController().navigateUp()
    }

    open fun setupView() {}

    /**
     * show loading dialog
     */
    open fun showLoadingDialog(): KProgressHUD? {
        return KProgressHUD(context).apply {
            if (loadingDialog?.isShowing == true) {
                loadingDialog?.dismiss()
            }
            if (context is LifecycleOwner) {
                @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
                fun onDestroy() {
                    this@apply.dismiss()
                    if (loadingDialog == this@apply) {
                        loadingDialog = null
                    }
                }

            }
            setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
            setLabel("Please wait")
            setCancellable(false)
            setAnimationSpeed(2)
            setDimAmount(0.5f)
            loadingDialog = this
            show()

        }
    }

    /**
     * dismiss Loadding Dialog
     */
    open fun dismissLoadingDialog() {
        if (loadingDialog?.isShowing == true) loadingDialog?.dismiss()
    }

    /**
     * show Dialog error
     */
    open fun showDialog(message: String?): IOSDialog.Builder? {
        return IOSDialog.Builder(context).apply {
            message(message)
                .cancelable(false)
                .title("Message")
                .positiveButtonText("OK")
                .positiveClickListener {
                    it.dismiss()
                }
                .enableAnimation(true)
                .build()
                .show()
        }
    }
}