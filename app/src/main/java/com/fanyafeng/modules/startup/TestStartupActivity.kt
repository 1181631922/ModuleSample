package com.fanyafeng.modules.startup

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.fanyafeng.modules.BaseActivity
import com.fanyafeng.modules.R
import com.ripple.log.tpyeextend.toLogD
import com.ripple.sdk.startup.AppInitializer
import com.ripple.sdk.startup.config.InitializerConfig
import com.ripple.tool.kttypelians.SuccessLambda
import com.ripple.tool.kttypelians.SuccessReturnLambda
import com.test.startup.LibraryC
import com.test.startup.LibraryD
import kotlinx.android.synthetic.main.activity_test_startup.*
import kotlinx.coroutines.*
import java.util.*
import java.util.concurrent.Callable
import java.util.concurrent.Executors
import kotlin.coroutines.CoroutineContext

class TestStartupActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_startup)

        initView()
        initData()
    }

    private fun initView() {


        startUp1.setOnClickListener {
//            AppInitializer.getInstance(applicationContext)
//                .initializeComponent(LibraryC::class.java)
            testInit()
        }

        startUp2.setOnClickListener {
            testInit2()
        }
    }

    private fun testInit() {
        val config =
            InitializerConfig.Builder(LibraryC::class.java).setIsMainThread(true).setDelayMillis(0L)
                .build()

        GlobalScope.launch(Dispatchers.Main) {
            launch(Dispatchers.IO) {
                delay(3000)
                Thread.currentThread().name.toLogD()
                AppInitializer.getInstance(applicationContext)
                    .initializeComponent(LibraryC::class.java)
            }

            launch(Dispatchers.Main) {
                Thread.currentThread().name.toLogD()
                AppInitializer.getInstance(applicationContext)
                    .initializeComponent(LibraryD::class.java)
            }
        }
    }

    private suspend fun suspendingAppInitializerIO(task: () -> Unit): Boolean =
        withContext(Dispatchers.IO) {
            delay(3000)
            Thread.currentThread().name.toLogD()
            try {
                task.invoke()
                true
            } catch (e: Exception) {
                false
            }
        }

    private suspend fun suspendingAppInitializerMain(task: () -> Unit): Boolean =
        withContext(Dispatchers.Main) {
            task.invoke()
            true
        }

    @JvmOverloads
    private suspend fun suspendingAppInitializer(
        context: CoroutineContext = Dispatchers.IO,
        task: () -> Boolean
    ): Boolean =
        withContext(context) {
            task.invoke()
        }

    private fun testInit2() {
        GlobalScope.launch {
            val isFinish = suspendingAppInitializer {
                AppInitializer.getInstance(applicationContext)
                    .initializeComponent(LibraryC::class.java)
                AppInitializer.getInstance(applicationContext).hasInitialized(LibraryC::class.java)
            }
            isFinish.toLogD()
        }


//        GlobalScope.launch {
//            val isFinish = suspendingAppInitializerIO {
//                AppInitializer.getInstance(applicationContext)
//                    .initializeComponent(LibraryC::class.java)
//            }
//            if (isFinish) {
//                AppInitializer.getInstance(applicationContext)
//                    .initializeComponent(LibraryD::class.java)
//            }
//        }


//        val list = mutableListOf<CallableItem>()
//
//        10.forEach {
//            list.add(CallableItem("我是第" + it + "个"))
//        }
//
//        val executor = Executors.newSingleThreadExecutor()
////        val resultList = executor.invokeAll(list)
////        resultList.forEach {
////            it.get().toLogD()
////        }
//        logD("时间:" + System.currentTimeMillis().long2Date())
//        list.forEach {
//            val result = executor.submit(it)
//            result.get().toLogD()
//        }
//        logD("时间:" + System.currentTimeMillis().long2Date())

        val executor = Executors.newSingleThreadScheduledExecutor()
//        executor.schedule()

    }

    private fun initData() {

    }

    class CallableItem(private val input: String) : Callable<String> {

        override fun call(): String {
            Thread.sleep(1000L)
            input.toLogD()
            return input
        }
    }
}

