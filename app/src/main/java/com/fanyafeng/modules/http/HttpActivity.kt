package com.fanyafeng.modules.http

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.fanyafeng.modules.R
import com.ripple.log.extend.logD
import kotlinx.android.synthetic.main.activity_http.*
import okhttp3.*
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import java.io.IOException

class HttpActivity : AppCompatActivity() {
    companion object {
        private val url = "http://10.12.16.198:8080/test/getUser"
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
        val urlBuilder = url.toHttpUrlOrNull()?.newBuilder()

        /**
         * 构造url
         * 使用了大量的构造者模式
         */
        val urlResult = urlBuilder?.build()
        urlResult.logD()

        /**
         * 构造request
         */
        val request = Request.Builder()
            .url(urlResult!!)
            .get()
        val requestResult = request.build()
        requestResult.logD()

        /**
         * 发起异步请求
         */
        client.newCall(requestResult).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                /**
                 * call为回传的结果
                 */
                e.toString().logD()
            }

            override fun onResponse(call: Call, response: Response) {
                val result = response.body?.string()
                result.logD()
                call.isCanceled().logD()
            }

        })
    }

    private fun httpGetASync2() {
        val client = OkHttpClient()
        val urlBuilder = url.toHttpUrlOrNull()?.newBuilder()
        urlBuilder?.addQueryParameter("uid", "uid")
        val urlResult = urlBuilder?.build()
        urlResult.logD()

        val request = Request.Builder()
            .url(urlResult!!)
            .get()
        val requestResult=request.build()


        client.newCall(requestResult).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                e.toString().logD()
            }

            override fun onResponse(call: Call, response: Response) {
                val result = response.body?.string()
                result.logD()
            }

        })
    }

    private fun httpGetSync1() {
        val client = OkHttpClient()
        val urlBuilder = url.toHttpUrlOrNull()?.newBuilder()
        urlBuilder?.addQueryParameter("uid", "uid")

        val urlHeader = Headers.Builder().add("code", "1234").add("name", "yinwen")

        val request = Request.Builder()
            .url(urlBuilder!!.build())
            .get()
            .headers(urlHeader.build())

        urlBuilder?.build().logD()
        request.build().headers.logD()

        Thread(
            Runnable {
                val response = client.newCall(request.build()).execute()
                if (response.code == 200) {
                    response.body?.string().logD()
                }
            }
        ).start()


    }
}
