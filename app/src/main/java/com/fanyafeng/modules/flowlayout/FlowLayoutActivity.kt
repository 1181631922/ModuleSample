package com.fanyafeng.modules.flowlayout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.fanyafeng.modules.BaseActivity
import com.fanyafeng.modules.R
import com.ripple.ui.flowview.IChooseModel
import com.ripple.ui.flowview.impl.ChooseItemView
import kotlinx.android.synthetic.main.activity_flow_layout.*

class FlowLayoutActivity : BaseActivity() {

    private val TAG = FlowLayoutActivity::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        title = "自适应Layout"
        setContentView(R.layout.activity_flow_layout)

        initView()
        initData()
    }

    var list = mutableListOf<IChooseModel>()

    private fun initView() {


        (0 until 5).forEach {
            val model = object : IChooseModel {
                override fun getChooseItemTitle(): String {
                    return "我是第" + it
                }

                override fun getChooseItemCheckable(): Boolean {
                    return it != 3
                }

                override fun getChooseItemChecked(): Boolean {
                    return false
                }
            }
            list.add(model)
            val itemView = ChooseItemView(this)
            chooseItemView.addItemView(itemView, model)
        }

        chooseItemView.onItemClickListener = { view, position, model ->
            Log.d(TAG, "被点击：" + position)
        }

        chooseItemView.onItemUnableClickListener = { view, position, model ->
            Log.d(TAG, "不能被点击的被点击了：" + position)
        }

    }

    private fun initData() {
        val viewList = mutableListOf<Pair<IChooseModel, ChooseItemView>>()
        (0 until 7).forEach { _ ->
            val model = object : IChooseModel {
                override fun getChooseItemTitle(): String {
                    return "我是新增的第二个view"
                }

                override fun getChooseItemCheckable(): Boolean {
                    return true
                }

                override fun getChooseItemChecked(): Boolean {
                    return false
                }
            }
            viewList.add(Pair(model, ChooseItemView(this)))
        }
        btn1.setOnClickListener {
            chooseItemView.updateView(viewList)
        }

        btn2.setOnClickListener {
            Log.d(TAG, "最后结果：" + chooseItemView.getSelectedResult())
        }

    }
}