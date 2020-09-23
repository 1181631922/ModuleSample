package com.fanyafeng.modules

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback

class BaseFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        callback = dispatcher.addCallback(this) {
//            //do
//        }
    }

    private val dispatcher by lazy { requireActivity().onBackPressedDispatcher }

    lateinit var callback: OnBackPressedCallback
}