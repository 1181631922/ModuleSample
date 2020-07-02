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
            val model = ChooseModel("我是第$it", it != 3, it == 0)
            list.add(model)
            val itemView = ChooseItemView(this)
            itemView.setInnerTagWrapContent()
            itemView.chooseViewUnselected=R.drawable.choose_view_normal
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
            val model = ChooseModel("我是第$it", it != 3, it == 0)
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
        (0 until 7).forEach { it ->
            val model = ChooseModel("我是新增的第$it 个view", true, (it == 3 || it == 4))
            val itemView = ChooseItemView(this)
//            itemView.setInnerTagWrapContent()
            viewList.add(Pair(model, itemView))
        }
        btn1.setOnClickListener {
            chooseItemView.updateView(viewList)
        }

        btn2.setOnClickListener {
            Log.d(TAG, "最后结果：" + chooseItemView.getSelectedResult().toString())
            Log.d(TAG, "所有列表状态：" + chooseItemView.getAllDataList().toString())
        }

    }
}

data class ChooseModel(var title: String, var checkable: Boolean, var checked: Boolean) :
    IChooseModel {
    override fun getChooseItemTitle(): String {
        return title
    }

    override fun getChooseItemCheckable(): Boolean {
        return checkable
    }

    override fun getChooseItemChecked(): Boolean {
        return checked
    }

    override fun setChooseItemChecked(isChecked: Boolean) {
        checked = isChecked
    }

}