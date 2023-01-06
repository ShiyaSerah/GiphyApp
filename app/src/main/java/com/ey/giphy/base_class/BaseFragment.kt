package com.ey.giphy.base_class

import android.content.BroadcastReceiver
import androidx.fragment.app.Fragment
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.ey.giphy.GiphyApplication
import com.ey.giphy.utils.CommonProgressDialog
import com.ey.giphy.utils.Logger
import java.lang.Exception

abstract class BaseFragment : Fragment() {
    private var networkReceiver: BroadcastReceiver? = null
    private var dialog: CommonProgressDialog? = null


    override fun onDestroyView() {
        super.onDestroyView()
        dismissProgressDialog()
        dialog = null
    }

    override fun onDestroy() {
        super.onDestroy()
        if (networkReceiver != null) {
            LocalBroadcastManager.getInstance(GiphyApplication.getContext()).unregisterReceiver(networkReceiver!!)
            networkReceiver = null
        }
    }

    fun showNonDismissedDialog(message: String?) {
        try {
            if (dialog == null) dialog = CommonProgressDialog(this.requireContext(), message!!)
            dialog!!.setCancelable(false)
            if (dialog != null && !dialog!!.isShowing) dialog!!.show()
        } catch (e: Exception) {
            Logger.e(TAG, e.message)
        }
    }

    fun dismissProgressDialog() {
        if (dialog != null && dialog!!.isShowing) dialog!!.dismiss()
    }

    companion object {
        private val TAG = BaseFragment::class.java.simpleName
    }
}