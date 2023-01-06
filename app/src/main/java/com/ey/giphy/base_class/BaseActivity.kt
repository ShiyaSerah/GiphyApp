package com.ey.giphy.base_class

import androidx.appcompat.app.AppCompatActivity
import com.ey.giphy.utils.CommonProgressDialog

open class BaseActivity:AppCompatActivity() {

    var dialog: CommonProgressDialog? = null

    protected fun showNonDismissedDialog(message: String?, isCancelable: Boolean) {
        if (dialog == null) dialog = CommonProgressDialog(this, message!!) else dialog!!.updateMessage(message!!)
        dialog!!.setCancelable(isCancelable)
        if (dialog != null && !dialog!!.isShowing)
            dialog!!.show()
    }

    protected fun dismissProgressDialog() {
        if (dialog != null && dialog!!.isShowing) dialog!!.dismiss()
    }
}