package com.fanyafeng.modules.datastore

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel


/**
 * Author: fanyafeng
 * Date: 2021/4/26 16:10
 * Email: fanyafeng@live.cn
 * Description:
 */
class DataStoreViewModel : ViewModel() {

    val clickButton3: MutableLiveData<Boolean> = MutableLiveData()

    fun clickButton3() {
        clickButton3.value = true
    }

    val clickButton4: MutableLiveData<Boolean> = MutableLiveData()

    fun clickButton4(boolean: Boolean) {
        clickButton4.value = boolean
    }

    val buttonText5: MutableLiveData<String> = MutableLiveData("查阅DataaStore")
    val clickButton5: MutableLiveData<Boolean> = MutableLiveData()

    fun clickButton5() {
        clickButton5.value = true
        buttonText5.value = "查阅成功"
    }

    val buttonText6: MutableLiveData<String> = MutableLiveData("测试协程返回值")
    val clickButton6: MutableLiveData<Boolean> = MutableLiveData()

    fun clickButton6() {
        clickButton6.value = true
        buttonText6.value = "请查看logcat打印值"
    }
}