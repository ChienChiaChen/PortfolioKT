package com.example.jason.portfoliokt.utils.view.dialogs

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import android.support.v7.app.AlertDialog
import com.example.jason.portfoliokt.utils.extensions.PERMISSION_MANUAL

class ManualEnablePermissionDialog(val activity: Activity, msgId: Int, val callback: (() -> Unit)? = null) {
    val dialog: AlertDialog

    init {
        dialog = AlertDialog.Builder(activity)
            .setTitle("Open ur permission")
            .setMessage("Ok?!")
            .setPositiveButton("Go to setting") { _, _ -> dialogConfirmed() }
            .setNegativeButton("Back") { _, _ -> dialogCancel() }
            .setCancelable(false)
            .create()
        dialog.show()
    }

    private fun dialogCancel() {
        callback?.invoke()
        dialog.dismiss()
    }

    private fun dialogConfirmed() {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.fromParts("package", activity.packageName, null))
        activity.startActivityForResult(intent, PERMISSION_MANUAL)
    }
}