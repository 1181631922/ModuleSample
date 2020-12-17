package com.fanyafeng.modules.filetest

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.preferencesKey
import androidx.datastore.preferences.createDataStore
import com.alibaba.fastjson.JSON
import com.ripple.tool.kttypelians.SuccessLambda
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.withContext
import java.io.IOException


/**
 * Author: fanyafeng
 * Date: 2020/11/20 16:19
 * Email: fanyafeng@live.cn
 * Description:
 */
class FileDataStoreManager(val context: Context) {
    /**
     * 创建数据表，如果已存在不会重新创建
     */
    private val welcomeDataStore = context.createDataStore("welcome")

    /**
     * 根据id获取 data model
     */
    suspend fun getModelByID(id: Int, successLambda: SuccessLambda<WelcomePageModel>) {
        val welcomeModelFlow: Flow<String> = welcomeDataStore.data
            .catch {
                if (it is IOException) {
                    it.printStackTrace()
                    /*
                    用于异常回复，如果catch到io异常，则入空参恢复flow
                     */
                    emit(emptyPreferences())
                } else {
                    throw it
                }
            }.map { preference ->
                val resultID: String = preference[preferencesKey(id.toString())] ?: ""
                resultID
            }
        welcomeModelFlow.flowOn(Dispatchers.IO).collectLatest {
            withContext(Dispatchers.Main) {
                val resultModel = JSON.parseObject(it, WelcomePageModel::class.java)
                successLambda?.invoke(resultModel)
            }
        }
    }

    /**
     * 根据id更新data model
     */
    suspend fun updateOrInsertModelByID(model: WelcomePageModel) {
        welcomeDataStore.edit {
            it[preferencesKey(model.id.toString())] = model.toJson()
        }
    }

    val welcomeModelFlow: Flow<String> = welcomeDataStore.data
        .catch {
            if (it is IOException) {
                it.printStackTrace()
                emit(emptyPreferences())
            } else {
                throw it
            }
        }.map { preference ->
            val resultID: String = preference[ID_FIRST] ?: ""
            resultID
        }


    companion object {
        /**
         * 根据key获取列表，列表的key为1
         */
        val ID_FIRST = preferencesKey<String>("1")
    }


}