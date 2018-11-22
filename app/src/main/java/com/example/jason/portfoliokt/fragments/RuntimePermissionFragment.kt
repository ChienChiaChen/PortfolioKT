package com.example.jason.portfoliokt.fragments


import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.jason.portfoliokt.R
import com.example.jason.portfoliokt.activities.BaseActivity
import com.example.jason.portfoliokt.utils.PermissionCase
import com.example.jason.portfoliokt.utils.extensions.PERMISSION_REQUEST
import com.example.jason.portfoliokt.utils.extensions.PERMISSION_WRITE_STORAGE
import com.example.jason.portfoliokt.utils.extensions.getPermissionString
import com.example.jason.portfoliokt.utils.extensions.toast
import com.example.jason.portfoliokt.utils.view.dialogs.ManualEnablePermissionDialog
import com.example.jason.portfoliokt.utils.view.dialogs.WritePermissionDialog
import kotlinx.android.synthetic.main.fragment_runtime_permission.*


class RuntimePermissionFragment : BaseFragment() {

    companion object {
        @JvmStatic
        fun newInstance() = RuntimePermissionFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_runtime_permission, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        askRuntimePermission.setOnClickListener {
            (activity as BaseActivity).handlePermission(PERMISSION_WRITE_STORAGE) { isGrenated ->
                when (isGrenated) {
                    PermissionCase.GRANTED -> context!!.toast("Granted !!")
                    PermissionCase.DENIED -> showAskPermissionDialog()
                    PermissionCase.NEVER_ASK_AGAIN -> showManualEnablePermissionDialog()
                }
            }
        }
    }

    private fun showManualEnablePermissionDialog() {
        ManualEnablePermissionDialog(context as Activity, 0)
    }

    private fun showAskPermissionDialog() {
        WritePermissionDialog(context!!, 0) { isPositive ->
            if (isPositive) {
                requestPermissions(arrayOf(context!!.getPermissionString(PERMISSION_WRITE_STORAGE)), PERMISSION_REQUEST)
            } else {
                context!!.toast("Woops...")
            }
        }
    }
}
