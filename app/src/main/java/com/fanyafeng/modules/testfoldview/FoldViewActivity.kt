package com.fanyafeng.modules.testfoldview

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.fanyafeng.modules.BaseActivity
import com.fanyafeng.modules.R
import kotlinx.android.synthetic.main.activity_fold_view.*

class FoldViewActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fold_view)
        title = "测试折叠布局"
        initView()
        initData()
    }

    private fun initView() {
        foldView1.setFoldViewOnlyUnfold(true)
        foldView1.setDescTextViewBackGroundColor(Color.RED)
        foldView1.setFoldControlTextViewBackGroundColor(Color.RED)
        foldView1.setFoldViewDesc("15201057723  123456a登录我的账号，去我的订单里面找带这个商品的订单就行，123456a登录我的账号，去我的订单里面找带这个商品的订单就行测试试测试测试测试测试测试测试")
    }

    private fun initData() {

    }
}