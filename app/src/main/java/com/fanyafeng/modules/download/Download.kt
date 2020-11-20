package com.fanyafeng.modules.download

import android.content.Context
import java.io.Serializable


/**
 * Author: fanyafeng
 * Date: 2020/11/17 18:32
 * Email: fanyafeng@live.cn
 * Description:
 */
interface Download : Serializable {
    fun download(
        context: Context,
        sourcePath: String,
        targetPath: String?,
        onResultCallBack: OnResultCallBack
    )

    fun download(context: Context, sourcePath: String, onResultCallBack: OnResultCallBack)

    interface OnResultCallBack {
        fun onError(message: String)

        fun onSuccess()
    }

    interface SimpleResultCallBack : OnResultCallBack {
        override fun onSuccess() {
        }

        override fun onError(message: String) {
        }
    }
}