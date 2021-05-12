package com.fanyafeng.modules.filetest

import androidx.datastore.preferences.core.preferencesKey

/**
 * Author: fanyafeng
 * Date: 2020/11/19 18:35
 * Email: fanyafeng@live.cn
 * Description:
 */
object FileDataStoreKeys {
    val SHOW_COMPLETED = preferencesKey<Boolean>("show_completed")

    val FILE_NAME = preferencesKey<String>("my_name")

    val FILE_TIME = preferencesKey<Long>("file_time")

    val FILE_ADD = preferencesKey<Long>("file_add")
}