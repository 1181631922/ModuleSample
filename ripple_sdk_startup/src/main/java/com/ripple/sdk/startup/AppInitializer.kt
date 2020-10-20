package com.ripple.sdk.startup

import android.content.ComponentName
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.os.TraceCompat
import com.ripple.sdk.startup.exception.StartupException
import com.ripple.sdk.startup.log.StartupLogger
import com.ripple.sdk.startup.provider.InitializationProvider
import java.util.*

/**
 * Author: fanyafeng
 * Date: 2020/9/25 10:09
 * Email: fanyafeng@live.cn
 * Description:
 */
class AppInitializer internal constructor(context: Context) {
    /**
     * 存放初始化过后的实例
     */
    private val mInitialized: MutableMap<Class<*>?, Any?>

    /**
     * 获取已经初始化的component的map
     */
    fun getInitializedMap() = mInitialized

    /**
     * 判断component是否已经初始化
     */
    fun hasInitialized(component: Class<out Initializer<*>>) = mInitialized.containsKey(component)

    /**
     * 存放已经初始化后的实例
     */
    private val mDiscovered: MutableSet<Class<out Initializer<*>?>>

    /**
     * 获取application的上下文
     */
    private val mContext: Context = context.applicationContext

    /**
     * Creates an instance of [AppInitializer]
     *
     * @param context The application context
     */
    init {
        mDiscovered = HashSet()
        mInitialized = HashMap()
    }

    companion object {
        // Tracing
        private const val SECTION_NAME = "Startup"

        /**
         * The [AppInitializer] instance.
         *
         * AppInitializer实例
         */
        @Volatile
        private var sInstance: AppInitializer? = null

        /**
         * Guards app initialization.
         *
         * app initialization 守卫
         */
        private val sLock = Any()

        /**
         * @param context The Application [Context]
         * @return The instance of [AppInitializer] after initialization.
         *
         * 单例，双check确定获取的是同一个实例
         */
        fun getInstance(context: Context): AppInitializer {
            if (sInstance == null) {
                synchronized(sLock) {
                    if (sInstance == null) {
                        sInstance = AppInitializer(context)
                    }
                }
            }
            return sInstance!!
        }
    }

    /**
     * Initializes a [Initializer] class type.
     *
     * @param component The [Class] of [Initializer] to initialize.
     * @param <T>       The instance type being initialized
     * @return The initialized instance
    </T> */
    fun <T> initializeComponent(component: Class<out Initializer<T>>): T {
        return doInitialize(component, HashSet())
    }

    /**
     * Returns `true` if the [Initializer] was eagerly initialized..
     *:
     * @param component The [Initializer] class to check
     * @return `true` if the [Initializer] was eagerly initialized.
     *
     * 判断是否已经完成初始化
     * 针对xml中预设配置
     */
    fun isEagerlyInitialized(component: Class<out Initializer<*>>): Boolean {
        // If discoverAndInitialize() was never called, then nothing was eagerly initialized.
        return mDiscovered.contains(component)
    }

    private fun <T> doInitialize(
        component: Class<out Initializer<*>>,
        initializing: MutableSet<Class<*>>//去重set
    ): T {
        /*
        确保线程安全
         */
        synchronized(sLock) {
            val isTracingEnabled = TraceCompat.isEnabled()
            return try {
                if (isTracingEnabled) {
                    // Use the simpleName here because section names would get too big otherwise.
                    TraceCompat.beginSection(component.simpleName)
                }
                if (initializing.contains(component)) {
                    val message = String.format(
                        "Cannot initialize %s. Cycle detected.", component.name
                    )
                    throw IllegalStateException(message)
                }
                val result: Any?
                if (!mInitialized.containsKey(component)) {
                    initializing.add(component)
                    try {
                        //利用反射，通过构造方法获取实例
                        val instance: Any? = component.getDeclaredConstructor().newInstance()
                        //强制类型转化，因为之前做了类型限制，强转不会出错
                        val initializer = instance as Initializer<*>?
                        //获取当前init实例所依赖的其他需要初始化的实例
                        val dependencies = initializer!!.dependencies()
                        if (dependencies.isNotEmpty()) {
                            for (clazz in dependencies) {
                                //迭代初始化，保证每个需要初始化以及当前实例所依赖的实例都能得到初始化
                                if (!mInitialized.containsKey(clazz)) {
                                    doInitialize<Any>(clazz!!, initializing)
                                }
                            }
                        }
                        if (StartupLogger.DEBUG) {
                            StartupLogger.i(String.format("Initializing %s", component.name))
                        }
                        //同步方法，当当前实例初始化完成后根据泛型获取相应实例
                        result = initializer.create(mContext)
                        if (StartupLogger.DEBUG) {
                            StartupLogger.i(String.format("Initialized %s", component.name))
                        }
                        //同步方法，初始化实例完成之后需要移除
                        initializing.remove(component)
                        //与上方!mInitialized.containsKey(component)相呼应
                        mInitialized[component] = result
                    } catch (throwable: Throwable) {
                        throw StartupException(throwable)
                    }
                } else {
                    //防止有相同的实例重复初始化，去重，加快初始化时间
                    result = mInitialized[component]
                }
                result as T
            } finally {
                TraceCompat.endSection()
            }
        }
    }

    internal fun discoverAndInitialize() {
        try {
            TraceCompat.beginSection(SECTION_NAME)
            val provider = ComponentName(
                mContext.packageName,
                InitializationProvider::class.java.name
            )
            val providerInfo = mContext.packageManager
                .getProviderInfo(provider, PackageManager.GET_META_DATA)
            val metadata = providerInfo.metaData
            val startup = mContext.getString(R.string.ripple_startup)
            if (metadata != null) {
                val initializing: MutableSet<Class<*>> = HashSet()
                val keys = metadata.keySet()
                for (key in keys) {
                    val value = metadata.getString(key, null)
                    if (startup == value) {
                        val clazz = Class.forName(key)
                        if (Initializer::class.java.isAssignableFrom(clazz)) {
                            val component = clazz as Class<out Initializer<*>>
                            mDiscovered.add(component)
                            if (StartupLogger.DEBUG) {
                                StartupLogger.i(String.format("Discovered %s", key))
                            }
                            //调用方法进行初始化
                            doInitialize<Any>(component, initializing)
                        }
                    }
                }
            }
        } catch (exception: PackageManager.NameNotFoundException) {
            throw StartupException(exception)
        } catch (exception: ClassNotFoundException) {
            throw StartupException(exception)
        } finally {
            TraceCompat.endSection()
        }
    }

}