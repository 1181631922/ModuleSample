package com.fanyafeng.modules.http

import android.os.Bundle
import com.fanyafeng.modules.BaseActivity
import com.fanyafeng.modules.R
import com.ripple.http.callback.OnHttpResult
import com.ripple.http.demo.RippleHttp
import com.ripple.http.exception.BaseException
import com.ripple.log.extend.logD
import com.ripple.log.tpyeextend.toLogD
import com.ripple.task.config.ProcessModel
import com.ripple.task.extend.handleTask
import kotlinx.android.synthetic.main.activity_http.*
import okhttp3.*
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import java.io.IOException

class HttpActivity : BaseActivity() {
    companion object {
        private const val GET_USER = "$BASE_URL/test/getUser"
        private const val GET_USER_LIST = "$BASE_URL/test/getUserList"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_http)
        initView()
        initData()
    }

    private fun initView() {
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
    }

    private fun initData() {

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
        RippleHttp.getInstance().get(GET_USER, object : OnHttpResult.OnHttpSimpleResult<User> {
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
        RippleHttp.getInstance()
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
        RippleHttp.getInstance().get<User>(GET_USER,
            success = {
                it.toLogD()
            })
    }

    /**
     * http get lambda请求封装
     */
    private fun httpGetASyncPackLambda2() {
        RippleHttp.getInstance().get<List<User>>(
            GET_USER_LIST,
            success = {
                it.toLogD()
            })
    }


    private fun httpGetASync2() {
        val client = OkHttpClient()
        val urlBuilder = GET_USER.toHttpUrlOrNull()?.newBuilder()
        urlBuilder?.addQueryParameter("uid", "uid")
        val urlResult = urlBuilder?.build()
        urlResult.toLogD()

        val request = Request.Builder()
            .url(urlResult!!)
            .get()
        val requestResult = request.build()


        client.newCall(requestResult).enqueue(object : Callback {
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
}


data class User(var name: String? = null)
