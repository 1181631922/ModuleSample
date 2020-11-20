package com.fanyafeng.modules.filetest

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.createDataStore
import androidx.lifecycle.asLiveData
import androidx.lifecycle.coroutineScope
import androidx.lifecycle.lifecycleScope
import com.alibaba.fastjson.JSON
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.Option
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.fanyafeng.modules.R
import com.fanyafeng.modules.download.dealfile.impl.GlideDownloadFile
import com.fanyafeng.modules.download.dealpicture.impl.GlideDownloadPicture
import com.fanyafeng.modules.download.Download
import com.ripple.dialog.extend.showToast
import com.ripple.log.tpyeextend.toLogD
import kotlinx.android.synthetic.main.activity_file_test.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.io.IOException
import java.util.*
import java.util.concurrent.atomic.AtomicBoolean
import kotlin.random.Random

class FileTestActivity : AppCompatActivity() {
    private var fileDataStoreManager: FileDataStoreManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_file_test)

        fileDataStoreManager = FileDataStoreManager(this)

        initView()
        initData()
    }

    private fun initView() {
        fileTest1.setOnClickListener {
            GlideDownloadPicture().download(
                this,
                "https://dss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=2534506313,1688529724&fm=26&gp=0.jpg",
                object : Download.SimpleResultCallBack {
                    override fun onSuccess() {
                        super.onSuccess()

                    }
                })
        }

//        Glide.with(this).load("/storage/emulated/0/DCIM/camera/FILE_20201118_110419WechatIMG24")
//            .into(fileTest2)

        fileTest3.setOnClickListener {
            GlideDownloadFile().download(this,
                "http://st.app.dmall.com/test/JetBrainsActivate.rar",
                object : Download.SimpleResultCallBack {
                    override fun onSuccess() {
                        super.onSuccess()
                        Toast.makeText(this@FileTestActivity, "文件下载成功", Toast.LENGTH_SHORT).show()
                    }
                })
        }

        fileTest4.setOnClickListener {

            GlideDownloadFile().download(this,
                "http://st.app.dmall.com/test/WechatIMG24",
                object : Download.SimpleResultCallBack {
                    override fun onSuccess() {
                        super.onSuccess()
                        Toast.makeText(this@FileTestActivity, "文件下载成功", Toast.LENGTH_SHORT).show()
                    }
                })
        }

        fileTest5.setOnClickListener {
//            val futureTarget =
//                Glide.with(this).asFile().load("http://st.app.dmall.com/test/JetBrainsActivate.rar")
//            val requestListener = object : RequestListener<File> {
//                override fun onLoadFailed(
//                    e: GlideException?,
//                    model: Any?,
//                    target: Target<File>?,
//                    isFirstResource: Boolean
//                ): Boolean {
//                    showToast("下载失败")
//                    return false
//                }
//
//                override fun onResourceReady(
//                    resource: File?,
//                    model: Any?,
//                    target: Target<File>?,
//                    dataSource: DataSource?,
//                    isFirstResource: Boolean
//                ): Boolean {
//
//                    showToast("下载成功")
//                    return false
//                }
//            }
//            futureTarget.addListener(requestListener)
//            futureTarget.preload()


            val requestListener = object : RequestListener<File> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<File>?,
                    isFirstResource: Boolean
                ): Boolean {
                    showToast("文件下载失败")
                    return false
                }

                override fun onResourceReady(
                    resource: File?,
                    model: Any?,
                    target: Target<File>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    showToast("文件下载完成")
                    resource?.let {
                        Glide.with(this@FileTestActivity).load(it.absoluteFile).into(fileTest2)
                    }
                    return false
                }

            }

            val timeOutOption = Option.memory(
                "com.fanyafeng.module.Timeout",
                2500
            )

            val requestOptions = RequestOptions.timeoutOf(2000)

            val requestBuilder =
                Glide.with(this).download("http://st.app.dmall.com/test/WechatIMG24")
                    .addListener(requestListener)
            requestBuilder.apply(requestOptions)
            val target = requestBuilder.preload()
//            Glide.with(this).clear(target)

            /*
            判断是否有缓存
             */
//            val requestBuilder =
//                Glide.with(this).asFile().load("http://st.app.dmall.com/test/WechatIMG24")
//            val futureTarget = requestBuilder.submit()
//            val isDone = futureTarget.isDone
//            print(isDone)
        }

        fileTest6.setOnClickListener {
            testDataStore()
//            lifecycleScope.launch(Dispatchers.IO) {
//                updateShowCompleted(true)
//            }
        }

        fileTest7.setOnClickListener {
            val job = lifecycleScope.launch(Dispatchers.IO) {
                dataStore.edit { preferences ->
                    preferences[FileDataStoreKeys.FILE_TIME] = System.currentTimeMillis()
                }
            }
            job.invokeOnCompletion {
                if (it == null) {
                    lifecycleScope.launch(Dispatchers.Main) {
                        showToast("任务完成")
                    }
                }
            }
        }

        fileTest8.setOnClickListener {
            val item1 = WelcomePageModel(1, "小樊", 30, "北京", "www.baidu.com")
            lifecycleScope.launch(Dispatchers.IO) {
                welcomeDataStore.edit { preferences ->
                    preferences[preferencesKey(item1.id.toString())] = item1.toJson()
                }
            }
        }

        var isUserClick = false



        fileTest9.setOnClickListener {
            isUserClick = true

            lifecycleScope.launch(Dispatchers.IO) {
                val updateItem = welcomeDataStore.data
                    .map { preferences ->
                        val result: String = preferences[preferencesKey("1")] ?: "未取到正确的值"
                        result
                    }
                updateItem.collectLatest {
                    withContext(Dispatchers.Main) {
                        if (isUserClick) {
                            showToast(it)
                            isUserClick = false
                        }
                    }
                }
            }

        }

        fileTest10.setOnClickListener {
            val item1 = WelcomePageModel(1, "小樊同学", 28, "北京市朝阳区", "https://www.baidu.com")
            lifecycleScope.launch(Dispatchers.IO) {
                welcomeDataStore.edit { preferences ->
                    preferences[preferencesKey(item1.id.toString())] = item1.toJson()
                }
            }
        }

        fileTest11.setOnClickListener {
            lifecycleScope.launch(Dispatchers.IO) {
                val item = WelcomePageModel(
                    1,
                    "小樊同学",
                    28,
                    "北京市朝阳区",
                    "https://www.baidu.com"
                )
                fileDataStoreManager?.updateOrInsertModelByID(item)
            }
        }

        fileTest12.setOnClickListener {
            lifecycleScope.launch(Dispatchers.IO) {
                fileDataStoreManager?.getModelByID(1) {
                    it.toJson().toLogD()
                }
            }
        }

        fileTest13.setOnClickListener {
            lifecycleScope.launch(Dispatchers.IO) {
                val item = WelcomePageModel(1, "小樊", 30, "北京市", "www.baidu.com")
                fileDataStoreManager?.updateOrInsertModelByID(item)
            }
        }

        fileTest14.setOnClickListener {
            modelResult?.toJson()?.toLogD()
        }
    }

    private fun initData() {
        observeWelcome()
    }

    private fun observeWelcome() {
        fileDataStoreManager?.welcomeModelFlow?.asLiveData()?.observe(this) {
            modelResult = JSON.parseObject(it, WelcomePageModel::class.java)
        }
    }

    private var modelResult: WelcomePageModel? = null

    private val dataStore: DataStore<Preferences> = createDataStore(name = "user")

    private val welcomeDataStore = createDataStore(name = "welcome_list")

    private fun testDataStore() {
        val userPreferencesFlow: Flow<UserPreferences> = dataStore.data
            .catch { exception ->
                if (exception is IOException) {
                    emit(emptyPreferences())
                } else {
                    throw exception
                }
            }
            .map { preferences ->
                // Get our show completed value, defaulting to false if not set:
                val showCompleted = preferences[FileDataStoreKeys.SHOW_COMPLETED] ?: false
                UserPreferences(showCompleted)
            }
        lifecycleScope.launch(Dispatchers.IO) {
            userPreferencesFlow.collectLatest {
                it.toLogD()
            }
        }


        lifecycleScope.launch(Dispatchers.IO) {
            val flow = dataStore.data.map {
                it[FileDataStoreKeys.SHOW_COMPLETED] ?: false
            }
            flow.collectLatest {
                withContext(Dispatchers.Main) {
                    showToast("走到此处$it")
                }
            }
        }


    }

    private suspend fun updateShowCompleted(showCompleted: Boolean) {
        dataStore.edit { preferences ->
            preferences[FileDataStoreKeys.SHOW_COMPLETED] = showCompleted
        }
    }

    override fun onDestroy() {
        super.onDestroy()
    }

}

data class UserPreferences(
    val showCompleted: Boolean
)



