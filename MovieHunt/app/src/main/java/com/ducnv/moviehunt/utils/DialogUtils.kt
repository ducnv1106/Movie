package com.ducnv.moviehunt.utils

import android.app.Dialog
import android.content.Context

import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent
import com.google.android.material.dialog.MaterialAlertDialogBuilder


/**
 * show single loading dialog
 */
//var loadingDialog: KProgressHUD? = null
//
//
//fun Context?.showLoadingDialog(
//    cancelable: Boolean = false,
//    canceledOnTouchOutside: Boolean = false
//): KProgressHUD? {
//    val context = this ?: return null
//    return  KProgressHUD(context).apply {
//        if (loadingDialog?.isShowing == true) {
//            loadingDialog?.dismiss()
//        }
//        if (context is LifecycleOwner) {
//           context.lifecycle.addObserver(object : LifecycleObserver{
//               @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
//               fun onDestroy() {
//                   this@apply.dismiss()
//                   if (loadingDialog == this@apply) {
//                       loadingDialog = null
//                   }
//               }
//           })
//        }
//        setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
//        setLabel("Please wait")
//        setCancellable(true)
//        setAnimationSpeed(2)
//        setDimAmount(0.5f)
////        loadingDialog=this
//        show()
//        loadingDialog=this
//
//        Log.e("moviedialog",this.isShowing.toString())
//        Log.e("moviedialog",loadingDialog?.isShowing.toString())
//    }
//
//}
//
//fun dismissLoadingDialog() {
//
//    if (loadingDialog?.isShowing == true) loadingDialog?.dismiss()
//}

/**
 * show single alert dialog
 */
var showingDialog: Dialog? = null
fun Context?.showDialog(
    title: String? = null,
    message: String? = null,
    textPositive: String? = null,
    positiveListener: (() -> Unit)? = null,
    textNegative: String? = null,
    negativeListener: (() -> Unit)? = null,
    cancelable: Boolean = false,
    canceledOnTouchOutside: Boolean = true
): AlertDialog? {
    val context = this ?: return null
    return MaterialAlertDialogBuilder(context).apply {
        setTitle(title)
        setMessage(message)
        setPositiveButton(textPositive) { dialog, which ->
            positiveListener?.invoke()
        }

        setNegativeButton(textNegative) { dialog, which ->
            negativeListener?.invoke()
        }
    }.create().apply {
        setCanceledOnTouchOutside(canceledOnTouchOutside)
        if (showingDialog?.isShowing == true) {
            showingDialog?.dismiss()
        }
        if (context is LifecycleOwner) {
            @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
            fun onDestroy() {
                this@apply.dismiss()
                if (showingDialog == this@apply) {
                    showingDialog = null
                }
            }
        }
        setOnDismissListener {
            showingDialog = null
        }
        showingDialog = this
        show()
    }
}
