package com.fanyafeng.modules.flowlayout

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import com.fanyafeng.modules.BaseActivity
import com.fanyafeng.modules.R
import com.ripple.tool.density.dp2px
import com.ripple.tool.extend.forEachAnchor
import com.ripple.tool.int.oneQuadra
import com.ripple.tool.screen.getScreenWidth
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
                    return it == 0
                }
            }
            list.add(model)
            val itemView = ChooseItemView(this)
            itemView.setInnerTagWrapContent()
            chooseItemView.addItemView(itemView, model)
        }


        chooseItemView.onItemAbleClickListener = { view, position, model ->
            Log.d(TAG, "被点击：" + position)
        }

        chooseItemView.onItemUnableClickListener = { view, position, model ->
            Log.d(TAG, "不能被点击的被点击了：" + position)
        }

        val itemWidth = (getScreenWidth() - 10.dp2px).oneQuadra
        val layoutParams =
            LinearLayout.LayoutParams(itemWidth, LinearLayout.LayoutParams.WRAP_CONTENT)
//        layoutParams.leftMargin = 5.dp2px
//        layoutParams.rightMargin = 5.dp2px
        splitFlowView.setMinChooseCount(1)
        4.forEachAnchor { it, isFirst, isLast ->
//            println("我是得几个：" + it)
//            println("是否是第一个：" + isFirst.toString())
//            println("是否是最后一个：" + isLast.toString())
            val model = object : IChooseModel {
                override fun getChooseItemTitle(): String {
                    return "我是第$it"
                }

                override fun getChooseItemCheckable(): Boolean {
                    return it != 3
                }

                override fun getChooseItemChecked(): Boolean {
                    return it == 0
                }
            }
            list.add(model)
            val itemView = ChooseItemView(this)
            itemView.layoutParams = layoutParams
            splitFlowView.addItemView(itemView, model)
        }


        splitFlowView.onItemClickListener =
            { first: View?, position: Int?, third: IChooseModel?, fourth: Boolean?, fifth: Boolean? ->
                println("被点击位置：" + position)
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
            Log.d(TAG, "最后结果：" + chooseItemView.getSelectedResult().toString())
        }

    }
}