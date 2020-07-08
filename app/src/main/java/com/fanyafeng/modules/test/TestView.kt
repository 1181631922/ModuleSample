package com.fanyafeng.modules.test

import android.content.Context
import android.util.AttributeSet
import com.ripple.ui.R
import com.ripple.ui.flowview.impl.ChooseItemView


/**
 * Author: fanyafeng
 * Data: 2020/7/6 14:54
 * Email: fanyafeng@live.cn
 * Description:
 */
class TestView(mContext: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
    ChooseItemView(mContext, attrs, defStyleAttr) {

    override var chooseViewUnselectable = R.drawable.choose_view_unselectable
}