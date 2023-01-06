package com.ey.giphy.utils

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Window
import androidx.databinding.DataBindingUtil
import com.ey.giphy.R
import com.ey.giphy.databinding.LoadingProgressBinding

class CommonProgressDialog(context: Context, var message: String) : Dialog(context) {

    var binding: LoadingProgressBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.loading_progress, null, false)
        setContentView(binding!!.root)

        binding!!.txtMessage.text = message
    }

    fun updateMessage(message: String){
        binding!!.txtMessage.text = message
    }

    override fun onStart() {
        super.onStart()
        val params = window!!.attributes
        params.width = context.resources.getDimension(com.intuit.sdp.R.dimen._250sdp).toInt()
        window!!.attributes = params
        window!!.setBackgroundDrawableResource(android.R.color.transparent)
    }
}