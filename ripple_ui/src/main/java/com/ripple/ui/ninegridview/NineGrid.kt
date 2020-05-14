package com.ripple.ui.ninegridview

import java.io.Serializable

/**
 * Author: fanyafeng
 * Data: 2020/5/13 13:53
 * Email: fanyafeng@live.cn
 * Description: 九宫格view需要哪些属性和变量
 * simple:[NineGridImpl]
 */
interface NineGrid : Serializable {

    /**
     * 设置九宫格间距
     */
    fun setDivide(divide: Int)

    /**
     * 获取九宫格间距
     */
    fun getDivide(): Int


    /**
     * 设置图片单张时显示的宽度
     */
    fun setSingleWidth(singleWidth: Int)

    /**
     * 获取单张图片时view的显示宽度
     */
    fun getSingleWidth(): Int

    fun setSingleImageRatio(ratio: Float)

    fun getSingleImageRatio(): Float
}