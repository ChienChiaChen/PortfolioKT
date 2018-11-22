package com.example.jason.portfoliokt.utils.extensions

import android.os.Looper

// permissions
const val PERMISSION_REQUEST = 100
const val PERMISSION_MANUAL = 101
const val PERMISSION_WRITE_STORAGE = 1


fun isOnMainThread() = Looper.myLooper() == Looper.getMainLooper()