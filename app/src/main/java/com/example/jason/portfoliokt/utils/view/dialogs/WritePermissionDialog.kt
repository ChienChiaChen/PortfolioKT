package com.example.jason.portfoliokt.utils.view.dialogs

import android.content.Context
import android.support.v7.app.AlertDialog

class WritePermissionDialog(context: Context, msgId: Int, val callback: ((isPositive: Boolean) -> Unit)? = null) {
    val dialog: AlertDialog

    init {
        dialog = AlertDialog.Builder(context)
            .setTitle("Permission needed")
            .setMessage("Request permission again")
            .setPositiveButton("Yes") { _, _ -> dialogConfirmed() }
            .setNegativeButton("Nope") { _, _ -> dialogCancel() }
            .create()
        dialog.show()
    }

    private fun dialogConfirmed() {
        callback?.invoke(true)
    }

    private fun dialogCancel() {
        callback?.invoke(false)
    }
}