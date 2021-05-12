package com.fanyafeng.modules.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences


/**
 * Author: fanyafeng
 * Date: 2021/4/26 10:38
 * Email: fanyafeng@live.cn
 * Description:
 */
interface CreateTable {
    /**
     * 创建data store表
     */
    fun createDataStore(context: Context, name: String): DataStore<Preferences>
}