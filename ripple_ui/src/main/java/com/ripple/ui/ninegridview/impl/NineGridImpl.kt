package com.ripple.ui.ninegridview.impl

import com.ripple.ui.ninegridview.NineGrid
import com.ripple.ui.ninegridview.NineItem

/**
 * Author: fanyafeng
 * Data: 2020/5/13 14:35
 * Email: fanyafeng@live.cn
 * Description: 配置的示例化代码
 */
class NineGridImpl @JvmOverloads constructor(
    private var divide: Int = 4,
    private var singleWidth: Int = 270,
    private var ratio: Float = 1F
) : NineGrid {
    override fun setDivide(divide: Int) {
        this.divide = divide
    }

    override fun getDivide(): Int {
        return divide
    }

    override fun setSingleWidth(singleWidth: Int) {
        this.singleWidth = singleWidth
    }

    override fun getSingleWidth(): Int {
        return singleWidth
    }

    override fun setSingleImageRatio(ratio: Float) {
        this.ratio = ratio
    }

    override fun getSingleImageRatio(): Float {
        return ratio
    }

    override fun toString(): String {
        return "NineGridImpl(divide=$divide, singleWidth=$singleWidth, ratio=$ratio)"
    }


}