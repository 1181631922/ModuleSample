package com.fanyafeng.modules.datastore

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.createDataStore
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import com.fanyafeng.modules.R
import com.fanyafeng.modules.databinding.ActivityDataStoreBinding
import com.fanyafeng.modules.datastore.impl.CreateTableImpl
import com.fanyafeng.modules.filetest.FileDataStoreKeys
import com.ripple.log.tpyeextend.toLogD
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import java.io.IOException

class DataStoreActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDataStoreBinding
    private val viewModel: DataStoreViewModel by lazy {
        ViewModelProvider(this).get(DataStoreViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        DataBindingUtil.inflate<>()
        binding = DataBindingUtil.setContentView(this, R.layout.activity_data_store)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        initData()
    }

    private val dataStore: DataStore<Preferences> = createDataStore(name = "com.fanyafeng.data")

    private fun initData() {
        var dataButton3: DataButton3? = null

        viewModel.clickButton3.observe(this, {
            this@DataStoreActivity.lifecycleScope.launch {
                dataStore.edit { preferences ->
                    val currentCounterValue = preferences[FileDataStoreKeys.FILE_ADD] ?: 0
                    currentCounterValue.toLogD()
                    preferences[FileDataStoreKeys.FILE_ADD] = 0L
                }
                dataButton3?.text = "删除完成"
                binding.buttonText3 = dataButton3
            }
        })

        val lambda: () -> Unit = {
            this@DataStoreActivity.lifecycleScope.launch {
                dataStore.edit { preferences ->
                    val currentCounterValue = preferences[FileDataStoreKeys.FILE_ADD] ?: 0
                    currentCounterValue.toLogD()
                    preferences[FileDataStoreKeys.FILE_ADD] = 0L
                }
                binding.buttonText3?.text = "删除完成"
            }
        }
        dataButton3 = DataButton3("删除DataStore", lambda)
        binding.buttonText1 = "创建DataStore"
        binding.buttonText2 = "增加DataStore"
        binding.buttonText3 = dataButton3
        binding.buttonText4 = "修改DataStore"

//        val createTable = CreateTableImpl()
//        val dataStore = createTable.createDataStore(
//            this@DataStoreActivity,
//            "com.fanyafeng.modules.datastore"
//        )

        val dataFlow: Flow<Long> = dataStore.data.catch {
            if (it is IOException) {
                it.printStackTrace()
                emit(emptyPreferences())
            } else {
                throw it
            }
        }.map { preference ->
            preference[FileDataStoreKeys.FILE_ADD] ?: -1L
        }

        dataFlow.asLiveData().observe(this, {
            it.toLogD("RippleTag livedate")
        })

        with(binding) {
            dataBtn1.setOnClickListener {
                this@DataStoreActivity.lifecycleScope.launch {
                    dataStore.edit { preferences ->
                        preferences[FileDataStoreKeys.FILE_ADD] = System.currentTimeMillis()
                    }
                    binding.buttonText1 = "创建完毕，请勿重复点击"
                }
            }

            dataBtn2.setOnClickListener {
                this@DataStoreActivity.lifecycleScope.launch {
                    dataStore.edit { preferences ->
                        val currentCounterValue = preferences[FileDataStoreKeys.FILE_ADD] ?: 0
                        currentCounterValue.toLogD("RippleTag currentCounterValue")
                        preferences[FileDataStoreKeys.FILE_ADD] = 333L
                    }

                    val watch: Flow<Long> = dataStore.data.map { preferences ->
                        preferences[FileDataStoreKeys.FILE_ADD].toLogD()
                        preferences[FileDataStoreKeys.FILE_ADD] ?: -11111L
                    }
                    watch.firstOrNull().toLogD("RippleTag firstOrNull")
                }
            }

//            dataBtn3.setOnClickListener {
//                this@DataStoreActivity.lifecycleScope.launch {
//                    dataStore.edit { preferences ->
//                        val currentCounterValue = preferences[FileDataStoreKeys.FILE_ADD] ?: 0
//                        currentCounterValue.toLogD()
//                        preferences[FileDataStoreKeys.FILE_ADD] = 0L
//                    }
//                }
//            }

//            dataBtn4.setOnClickListener {
//                this@DataStoreActivity.lifecycleScope.launch {
//                    dataStore.edit { preferences ->
//                        val currentCounterValue = preferences[FileDataStoreKeys.FILE_ADD] ?: 0
//                        currentCounterValue.toLogD()
//                        preferences[FileDataStoreKeys.FILE_ADD] = -1L
//                    }
//                }
//            }

//            dataBtn5.setOnClickListener {
//                val watch: Flow<Long> = dataStore.data.map { preferences ->
//                    preferences[FileDataStoreKeys.FILE_ADD] ?: -1L
//                }
//                this@DataStoreActivity.lifecycleScope.launch {
//                    watch.collectLatest {
//                        it.toLogD("RippleTag collectLatest")
//                    }
//                }
//
//            }

        }

        viewModel.clickButton4.observe(this, {
            this@DataStoreActivity.lifecycleScope.launch {
                dataStore.edit { preferences ->
                    val currentCounterValue = preferences[FileDataStoreKeys.FILE_ADD] ?: 0
                    currentCounterValue.toLogD()
                    preferences[FileDataStoreKeys.FILE_ADD] = -1L
                }
                if (it) {
                    binding.buttonText4 = "修改DataStore成功"
                }
            }

        })

        viewModel.clickButton5.observe(this, {
            val watch: Flow<Long> = dataStore.data.map { preferences ->
                preferences[FileDataStoreKeys.FILE_ADD] ?: -1L
            }
            this@DataStoreActivity.lifecycleScope.launch {
                watch.collectLatest {
                    it.toLogD("RippleTag collectLatest")
                }
            }
        })

        viewModel.clickButton6.observe(this, {
            if (it) {
                "开始打印".toLogD("RippleTag start")
                getResult0().toLogD("RippleTag getResult0()")
                this@DataStoreActivity.lifecycleScope.launch {
                    getResult1().toLogD("RippleTag getResult1()")
                    getResult2().toLogD("RippleTag getResult2()")
                }
            }
        })
    }

    private fun getResult0() = runBlocking {
        withTimeoutOrNull(1000){
            delay(2000)
            getSuspendResult()
        }
    }

    private suspend fun getResult1(): Int {
        val result = GlobalScope.async {
            getSuspendResult()
        }
        result.await()
        return result.getCompleted()
    }

    private suspend fun getResult2(): Int {
        var result: Int = 0
        val job = GlobalScope.launch {
            result = getSuspendResult()
        }
        job.join()
        return result
    }


    private suspend fun getSuspendResult(): Int {
        val watch: Flow<Long> = dataStore.data.map { preferences ->
            preferences[FileDataStoreKeys.FILE_ADD].toLogD()
            preferences[FileDataStoreKeys.FILE_ADD] ?: -11111L
        }
        return watch.firstOrNull()?.toInt() ?: 0
    }
}

data class DataButton3(var text: String, val lambda: (() -> Unit))