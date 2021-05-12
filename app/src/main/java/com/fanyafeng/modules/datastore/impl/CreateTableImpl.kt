package com.fanyafeng.modules.datastore.impl

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.createDataStore
import com.fanyafeng.modules.datastore.CreateTable


/**
 * Author: fanyafeng
 * Date: 2021/4/26 10:40
 * Email: fanyafeng@live.cn
 * Description:
 */
class CreateTableImpl : CreateTable {
    override fun createDataStore(context: Context, name: String): DataStore<Preferences> {
        return context.createDataStore(name = name)
    }
}