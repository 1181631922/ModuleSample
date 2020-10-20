package com.ripple.sdk.startup.provider

import android.content.ContentProvider
import android.content.ContentValues
import android.database.Cursor
import android.net.Uri
import androidx.annotation.RestrictTo
import com.ripple.sdk.startup.AppInitializer
import com.ripple.sdk.startup.exception.StartupException

/**
 * Author: fanyafeng
 * Date: 2020/9/25 10:09
 * Email: fanyafeng@live.cn
 * Description:
 */
@RestrictTo(RestrictTo.Scope.LIBRARY)
class InitializationProvider : ContentProvider() {
    override fun onCreate(): Boolean {
        val context = context
        if (context != null) {
            //自动初始化，根据配置文件的provider进行解析
            AppInitializer.getInstance(context).discoverAndInitialize()
        } else {
            throw StartupException("Context cannot be null")
        }
        return true
    }

    override fun query(
        uri: Uri,
        projection: Array<String>?,
        selection: String?,
        selectionArgs: Array<String>?,
        sortOrder: String?
    ): Cursor? {
        throw IllegalStateException("Not allowed.")
    }

    override fun getType(uri: Uri): String? {
        throw IllegalStateException("Not allowed.")
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        throw IllegalStateException("Not allowed.")
    }

    override fun delete(
        uri: Uri,
        selection: String?,
        selectionArgs: Array<String>?
    ): Int {
        throw IllegalStateException("Not allowed.")
    }

    override fun update(
        uri: Uri,
        values: ContentValues?,
        selection: String?,
        selectionArgs: Array<String>?
    ): Int {
        throw IllegalStateException("Not allowed.")
    }
}