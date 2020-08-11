package com.fanyafeng.modules.http

import android.os.Bundle
import com.fanyafeng.modules.BaseActivity
import com.fanyafeng.modules.R
import com.ripple.http.RippleHttp
import com.ripple.http.callback.OnHttpResult
import com.ripple.http.RippleHttpClient
import com.ripple.http.demo.RippleHttpTest
import com.ripple.http.exception.BaseException
import com.ripple.log.extend.logD
import com.ripple.log.tpyeextend.toLogD
import kotlinx.android.synthetic.main.activity_http.*
import okhttp3.*
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import java.io.IOException
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.TimeUnit

class HttpActivity : BaseActivity() {
    companion object {
        private const val GET_USER = "$BASE_URL/test/getUser"
        private const val GET_USER_LIST = "$BASE_URL/test/getUserList"
        private const val GET_USER_BY_ID = "$BASE_URL/get/getUserById"
        private const val POST_USER_BY_ID = "$BASE_URL/post/getUserById"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_http)
        initView()
        initData()
    }

    private fun initView() {
        httpTest.setOnClickListener {
            httpPostASync()
        }

        httpTest3.setOnClickListener {
            httpPostASync2()
        }


        //httpGet异步请求
        httpGetASync1.setOnClickListener {
            httpGetASync1()
        }

        /**
         * model，List<Model>都涵盖了
         */

        /**
         * callback model 类型
         */
        httpGetASyncPackCallback1.setOnClickListener {
            httpGetASyncPackCallback1()
        }

        /**
         * callback List<Model> 类型
         */
        httpGetASyncPackCallback2.setOnClickListener {
            httpGetASyncPackCallback2()
        }

        /**
         * lambda model 类型
         */
        httpGetASyncPackLambda1.setOnClickListener {
            httpGetASyncPackLambda1()
        }

        /**
         * lambda List<Model> 类型
         */
        httpGetASyncPackLambda2.setOnClickListener {
            httpGetASyncPackLambda2()
        }

        //httpGet异步请求带param
        httpGetASync2.setOnClickListener {
            httpGetASync2()
        }

        httpGetSync.setOnClickListener {
            httpGetSync1()
        }

        httpTest1.setOnClickListener {
            val param = UserParam()
            RippleHttp.get(param, object : OnHttpResult.OnHttpSimpleResult<User> {
                override fun onItemSuccess(successResult: User) {
                    successResult.name.toLogD()
                }
            })
        }

        httpTest2.setOnClickListener {
            val param = UserListParam()
            RippleHttp.get(param, object : OnHttpResult.OnHttpSimpleResult<List<User>> {
                override fun onItemSuccess(successResult: List<User>) {
                    successResult.toLogD()
                }

                override fun onItemFailed(failedResult: BaseException) {
                    super.onItemFailed(failedResult)
                    failedResult.message.toLogD()
                }
            })


        }
    }

    private fun initData() {
//        (::httpGetASync1).invoke()
    }

    private fun httpGetASync1() {
        val client = OkHttpClient()
        val urlBuilder = GET_USER.toHttpUrlOrNull()?.newBuilder()

        /**
         * 构造url
         * 使用了大量的构造者模式
         */
        val urlResult = urlBuilder?.build()
        urlResult.toLogD()

        /**
         * 构造request
         */
        val request = Request.Builder()
            .url(urlResult!!)
            .get()
        val requestResult = request.build()
        requestResult.toLogD()

        /**
         * 发起异步请求
         */
        client.newCall(requestResult).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                /**
                 * call为回传的结果
                 */
                e.toString().toLogD()
            }

            override fun onResponse(call: Call, response: Response) {
                val result = response.body?.string()
                result.toLogD()
                call.isCanceled().toLogD()

                logD("测试")
            }

        })
    }

    /**
     * http get callback请求封装
     */
    private fun httpGetASyncPackCallback1() {
        RippleHttpTest.getInstance().get(GET_USER, object : OnHttpResult.OnHttpSimpleResult<User> {
            override fun onItemStart(startResult: Unit) {
                super.onItemStart(startResult)
                logD("HTTP任务开始", "Http请求封装：")
            }

            override fun onItemDoing(doingResult: Long) {
                super.onItemDoing(doingResult)
                doingResult.toLogD("Http请求封装：")
            }

            override fun onItemSuccess(successResult: User) {
                successResult.toLogD("Http请求封装：")
            }

            override fun onItemFailed(failedResult: BaseException) {
                super.onItemFailed(failedResult)
                failedResult.toLogD("Http请求封装：")
            }

            override fun onItemFinish(finishResult: Boolean) {
                super.onItemFinish(finishResult)
                finishResult.toLogD("Http请求封装：")
            }
        })
    }

    /**
     * http get callback请求封装
     */
    private fun httpGetASyncPackCallback2() {
        RippleHttpTest.getInstance()
            .get(GET_USER_LIST, object : OnHttpResult.OnHttpSimpleResult<List<User>> {
                override fun onItemSuccess(successResult: List<User>) {
                    successResult.toLogD("Http请求封装：")
                }
            })
    }

    /**
     * http get lambda请求封装
     */
    private fun httpGetASyncPackLambda1() {
        RippleHttpTest.getInstance().get<User>(GET_USER,
            success = {
                it.toLogD()
            })
    }

    /**
     * http get lambda请求封装
     */
    private fun httpGetASyncPackLambda2() {
        RippleHttpTest.getInstance().get<List<User>>(
            GET_USER_LIST,
            success = {
                it.toLogD()
            })
    }


    /**
     * get请求测试
     * 请求超时
     *
     * 包含以下几方面：
     * params
     * header
     * path
     */
    private fun httpGetASync2() {
        /**
         * 读取超时时间
         */
        val READ_TIMEOUT = 100_000L

        /**
         * 写入超时时间
         */
        val WRITE_TIMEOUT = 60_000L

        /**
         * 链接时间
         */
        val CONNECT_TIMEOUT = 60_000L

        /**
         * 构造OkHttpClient
         */
        val client = RippleHttpClient.getInstance().newBuilder()
        client.readTimeout(READ_TIMEOUT, TimeUnit.MILLISECONDS)
        client.writeTimeout(WRITE_TIMEOUT, TimeUnit.MILLISECONDS)
        client.connectTimeout(CONNECT_TIMEOUT, TimeUnit.MILLISECONDS)

        val clientResult = client.build()

        /**
         * 构造带有path的url
         * 这里采用buffer，主要因为它是线程安全的而且高效
         */
        val urlBuffer = StringBuffer()
        urlBuffer.append(GET_USER_BY_ID)
        urlBuffer.append("/")
        urlBuffer.append("myname")
        val httpUrl = urlBuffer.toString()
        val urlBuilder = httpUrl.toHttpUrlOrNull()?.newBuilder()

        /**
         * 构造header请求头
         * 这里采用ConcurrentHashMap，考虑到线程安全，防止重复添加相同的key，value
         */
        val hashMap: ConcurrentHashMap<String, Any> = ConcurrentHashMap()
        val headerBuilder = Headers.Builder()
        hashMap.forEach { (key: String, value: Any) ->
            headerBuilder.add(key, value.toString())
        }
        val urlHeaderResult = headerBuilder.build()

        /**
         * 构造params
         * id为int类型
         */
        urlBuilder?.addQueryParameter("id", "666")
        val urlResult = urlBuilder?.build()
        urlResult.toLogD()

        val request = Request.Builder()
            //header构建
            .headers(urlHeaderResult)
            //url构建
            .url(urlResult!!)
            //get请求
            .get()
        val requestResult = request.build()


        clientResult.newCall(requestResult).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                e.toString().toLogD()
            }

            override fun onResponse(call: Call, response: Response) {
                val result = response.body?.string()
                result.toLogD()
            }
        })
    }

    private fun httpGetSync1() {
        val client = OkHttpClient()
        val urlBuilder = GET_USER.toHttpUrlOrNull()?.newBuilder()
        urlBuilder?.addQueryParameter("uid", "uid")

        val urlHeader = Headers.Builder().add("code", "1234").add("name", "yinwen")

        val request = Request.Builder()
            .url(urlBuilder!!.build())
            .get()
            .headers(urlHeader.build())

        urlBuilder.build().toLogD()
        request.build().headers.toLogD()
        Thread(
            Runnable {
                /**
                 * 呼叫请求
                 * 此时可以获取到请求是否成发送
                 * 以及对请求进行操作
                 */
                val realCall = client.newCall(request.build())

                /**
                 * 请求后的返回
                 * 通过此判断请求是否成功
                 */
                val response = realCall.execute()
                if (response.isSuccessful) {
                    response.body?.string().toLogD()
                } else {
                    "请求失败：".toLogD()
                }
            }
        ).start()


    }

    /**
     * get请求测试
     * 请求超时
     *
     * 包含以下几方面：
     * params
     * header
     * path
     */
    private fun httpPostASync() {
        val userListPostParam = UserListPostParam()
        userListPostParam.addPathParam("myName")
        RippleHttp.post(userListPostParam, object : OnHttpResult.OnHttpSimpleResult<List<User>> {
            override fun onItemSuccess(successResult: List<User>) {
                super.onItemSuccess(successResult)
                successResult.toLogD()
            }
        })
    }

    private fun httpPostASync2() {
        val userListPostParam = UserListPostIdParam()
        val userListPostParam1 = UserListPostParam()
        userListPostParam1.addPathParam("myName")
        RippleHttp.post(userListPostParam, object : OnHttpResult.OnHttpSimpleResult<List<User>> {
            override fun onItemSuccess(successResult: List<User>) {
                super.onItemSuccess(successResult)
                successResult.toLogD()
                logD("结束时间")
            }

            override fun onItemFinish(finishResult: Boolean) {
                super.onItemFinish(finishResult)
                userListPostParam.setCancelNext(true)
            }
        }).thenPost(userListPostParam1, object : OnHttpResult.OnHttpSimpleResult<List<User>> {
            override fun onItemStart(startResult: Unit) {
                super.onItemStart(startResult)
                logD("开始时间")
            }

            override fun onItemSuccess(successResult: List<User>) {
                super.onItemSuccess(successResult)
                successResult.toLogD()
            }

            override fun onItemFinish(finishResult: Boolean) {
                super.onItemFinish(finishResult)
                logD("任务完成")
            }
        })
    }
}


data class User(var name: String? = null)
