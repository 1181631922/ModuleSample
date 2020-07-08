package com.fanyafeng.modules.flowlayout

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import android.widget.RelativeLayout
import android.widget.TextView
import com.fanyafeng.modules.R
import com.ripple.tool.density.dp2px
import com.ripple.ui.flowview.IChooseItemView
import com.ripple.ui.flowview.IChooseModel
import com.ripple.ui.flowview.impl.ChooseItemView
import kotlinx.android.synthetic.main.custom_item_choose_view_layout.view.*
import kotlinx.android.synthetic.main.custom_item_choose_view_layout.view.rippleChooseItemView


/**
 * Author: fanyafeng
 * Data: 2020/6/28 19:53
 * Email: fanyafeng@live.cn
 * Description: 单个item的选中view默认实现
 *
 */
class SpecificationChooseView @JvmOverloads constructor(
    private val mContext: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) :
    ChooseItemView(mContext, attrs, defStyleAttr) {


    /**
     * 标签背景的三种状态
     */
    override var chooseViewUnselectable = R.drawable.specification_choose_view_unselectable
    override var chooseViewSelected = R.drawable.specification_choose_view_selected
    override var chooseViewUnselected = R.drawable.specification_choose_view_unselected

//    /**
//     * 标签内部字体的三种颜色
//     */
//    var unselectableTagColor = Color.parseColor("#cccccc")
//    var selectedTagColor = Color.parseColor("#ff680a")
//    var unselectedTagColor = Color.BLACK

    override fun onFinishInflate() {
        getTagView().height = 60.dp2px
        super.onFinishInflate()
    }



}