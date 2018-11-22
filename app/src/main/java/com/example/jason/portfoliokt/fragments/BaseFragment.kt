package com.example.jason.portfoliokt.fragments

import android.support.v4.app.Fragment

open class BaseFragment : Fragment() {
    open fun onBackPressed() = false
}