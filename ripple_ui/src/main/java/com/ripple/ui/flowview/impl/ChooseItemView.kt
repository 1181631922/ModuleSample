package com.ripple.ui.flowview.impl

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.ripple.ui.R
import com.ripple.ui.flowview.IChooseItemView
import com.ripple.ui.flowview.IChooseModel
import kotlinx.android.synthetic.main.item_choose_view_layout.view.*


/**
 * Author: fanyafeng
 * Data: 2020/6/28 19:53
 * Email: fanyafeng@live.cn
 * Description:
 */
open class ChooseItemView @JvmOverloads constructor(
    private val mContext: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) :
    FrameLayout(mContext, attrs, defStyleAttr), IChooseItemView {

    init {
        LayoutInflater.from(mContext).inflate(R.layout.item_choose_view_layout, this)
        rippleChooseItemView.setBackgroundResource(R.drawable.choose_view_unselected)
    }

    protected var mChecked = false
    protected var mCheckable = true

    fun <T : IChooseModel> initData(model: T) {
        rippleChooseItemView.text = model.getChooseItemTitle()
        setCheckable(model.getChooseItemCheckable())
        setChecked(model.getChooseItemChecked())
    }

    fun getChooseItemView() = getChildAt(0)

    override fun isCheckable(): Boolean {
        return mCheckable
    }

    override fun setCheckable(isCheckable: Boolean) {
        mCheckable = isCheckable
        if (!mCheckable) {
            rippleChooseItemView.setBackgroundResource(R.drawable.choose_view_unselectable)
        }
    }

    override fun isChecked(): Boolean {
        return if (!mCheckable) {
            false
        } else {
            mChecked
        }
    }

    override fun setChecked(isChecked: Boolean) {
        if (mCheckable) {
            mChecked = isChecked
            if (mChecked) {
                rippleChooseItemView.setBackgroundResource(R.drawable.choose_view_selected)
            } else {
                rippleChooseItemView.setBackgroundResource(R.drawable.choose_view_unselected)
            }
        } else {
            rippleChooseItemView.setBackgroundResource(R.drawable.choose_view_unselectable)
        }
    }

    override fun toggle() {
        if (mCheckable) {
            setChecked(!mChecked)
        }
    }
}