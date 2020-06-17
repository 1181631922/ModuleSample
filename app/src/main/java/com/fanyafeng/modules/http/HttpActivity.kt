package com.fanyafeng.modules.http

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.fanyafeng.modules.R
import kotlinx.android.synthetic.main.activity_http.*

class HttpActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_http)
        initView()
        initData()
    }

    private fun initView() {
        //httpGet请求
        btn1.setOnClickListener {

        }
    }

    private fun initData() {

    }
}
