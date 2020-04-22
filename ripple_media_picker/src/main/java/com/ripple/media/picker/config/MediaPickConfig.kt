package com.ripple.media.picker.config

import java.io.Serializable

/**
 * Author: fanyafeng
 * Data: 2020/4/21 17:34
 * Email: fanyafeng@live.cn
 * Description:
 */
interface MediaPickConfig:Serializable {
    /**
     * 设置最大的选取数量
     */
    fun getCount(): Int

    /**
     * 设置选取的最大的大小
     */
    fun getSize(): Long

    /**
     * 设置选取方式，单选or多选
     */
    fun getChooseType(): ChooseType

    enum class ChooseType {
        //单选
        SINGLE_CHOOSE_TYPE,
        //多选
        MULTIPLE_CHOOSE_TYPE
    }
}