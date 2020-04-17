package com.fanyafeng.modules.mediapick

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.fanyafeng.modules.R

class MediaPickActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_media_pick)
        title = "媒体库选择选择"

        initView()
        initData()
    }

    private fun initView() {

    }

    private fun initData() {

    }
}
