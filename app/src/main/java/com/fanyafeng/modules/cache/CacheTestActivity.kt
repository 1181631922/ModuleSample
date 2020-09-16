package com.fanyafeng.modules.cache

import android.content.Context
import android.os.Bundle
import android.os.Handler
import com.bumptech.glide.Glide
import com.facebook.drawee.backends.pipeline.Fresco
import com.fanyafeng.modules.BaseActivity
import com.fanyafeng.modules.R
import com.ripple.log.tpyeextend.toLogD
import com.ripple.sdk.cache.RippleCache
import kotlinx.android.synthetic.main.activity_cache_test.*
import okhttp3.OkHttpClient
import org.json.JSONObject
import java.math.BigInteger
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

class CacheTestActivity : BaseActivity() {

    private val myJson = """{
                	"code": "0000",
                	"message": "成功",
                	"data": null,
                	"success": true
                }"""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cache_test)
        title = "测试缓存"
        initView()
        initData()
    }

    private fun initView() {
        OkHttpClient

        cacheBtn1.setOnClickListener {
            val sp = getSharedPreferences("test_cache1", Context.MODE_PRIVATE)
            val editor = sp.edit()
            val json = myJson
            editor.putString("json", json)
            editor.apply()

            RippleCache.getInstance().lruCache?.put("xiaofan", "小樊")
            RippleCache.getInstance().lruCache?.put("xiaofan1", "小樊")
            RippleCache.getInstance().lruCache?.put("xiaofan2", "小樊")
            RippleCache.getInstance().lruCache?.put("xiaofan3", "小樊")
            RippleCache.getInstance().lruCache?.put("xiaofan4", "小樊")
            RippleCache.getInstance().lruCache?.put("xiaofan5", "小樊")
            RippleCache.getInstance().lruCache?.put("xiaofan", "小樊")
            RippleCache.getInstance().lruCache?.put("xiaofan2", "小樊")
//            RippleCache.getInstance().lruCache?.commit(this)
            RippleCache.commit(this)
        }

        cacheBtn2.setOnClickListener {
            val sp = getSharedPreferences("test_cache1", Context.MODE_PRIVATE)
            val json = sp.getString("json", "获取失败")?.toLogD()
            sp.getString("json5", "获取失败")
            json?.let {
                val jsonObject = JSONObject(it)
                jsonObject.optString("message").toLogD()
                stringMD5(it).toLogD()
            }
            stringMD5(myJson).toLogD()

        }

        cacheBtn3.setOnClickListener {
            val sp = getSharedPreferences("test_cache1", Context.MODE_PRIVATE)
            val editor = sp.edit()
            editor.putString("json3", "newjson3")
            editor.apply()
        }
    }

    private fun initData() {
    }

    private fun stringMD5(input: String): String? {
        return try {

            // 拿到一个MD5转换器（如果想要SHA1参数换成”SHA1”）
            val messageDigest =
                MessageDigest.getInstance("MD5")

            // 输入的字符串转换成字节数组
            val inputByteArray = input.toByteArray()


            // inputByteArray是输入字符串转换得到的字节数组
            messageDigest.update(inputByteArray)


            // 转换并返回结果，也是字节数组，包含16个元素
            val resultByteArray = messageDigest.digest()


            // 字符数组转换成字符串返回
            byteArrayToHex(resultByteArray)
        } catch (e: NoSuchAlgorithmException) {
            null
        }
    }

    private fun byteArrayToHex(byteArray: ByteArray): String? {

        // 首先初始化一个字符数组，用来存放每个16进制字符
        val hexDigits = charArrayOf(
            '0',
            '1',
            '2',
            '3',
            '4',
            '5',
            '6',
            '7',
            '8',
            '9',
            'A',
            'B',
            'C',
            'D',
            'E',
            'F'
        )

        // new一个字符数组，这个就是用来组成结果字符串的（解释一下：一个byte是八位二进制，也就是2位十六进制字符（2的8次方等于16的2次方））
        val resultCharArray = CharArray(byteArray.size * 2)

        // 遍历字节数组，通过位运算（位运算效率高），转换成字符放到字符数组中去
        var index = 0
        for (b in byteArray) {
            resultCharArray[index++] = hexDigits[b.toInt() ushr 4 and 0xf]
            resultCharArray[index++] = hexDigits[b.toInt() and 0xf]
        }

        // 字符数组组合成字符串返回
        return String(resultCharArray)
    }

}