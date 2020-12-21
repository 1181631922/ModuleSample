package com.fanyafeng.modules.testflow

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.fanyafeng.modules.R
import kotlinx.android.synthetic.main.activity_test_flow.*
import kotlinx.coroutines.flow.Flow

class TestFlowActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_flow)

        initView()
        initData()
    }

    private fun initView() {
        flow1.setOnClickListener {
            flow1()
        }
    }

    private fun initData() {

    }

    private fun flow1() {

    }
}



