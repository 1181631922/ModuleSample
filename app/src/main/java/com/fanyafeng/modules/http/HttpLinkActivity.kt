package com.fanyafeng.modules.http

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.TypeEvaluator
import android.animation.ValueAnimator
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.fanyafeng.modules.BaseActivity
import com.fanyafeng.modules.R
import com.ripple.http.RippleHttp
import com.ripple.http.callback.OnHttpResult
import com.ripple.http.extend.httpGet
import com.ripple.http.extend.httpPost
import com.ripple.log.tpyeextend.toLogD
import com.ripple.tool.density.dp2px
import kotlinx.android.synthetic.main.activity_http_link.*
import kotlinx.coroutines.*


class HttpLinkActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_http_link)
        title = "http链式调用"
        initView()
        initData()
    }

    private fun initView() {
        httpLink1.setOnClickListener {
            httpGet {
                val userParam = UserParam()
                params = userParam

                onSuccess<User> {
                    it.name.toLogD()
                }
            }.withGet {
                val userParam = UserParam()
                params = userParam

                onSuccess<User> {
                    it.name.toLogD("随行get请求")
                    userParam.setCancelNext(true)
                }

                onFailed {
                    //取消之后的请求
                    userParam.setCancelNext(true)
                }
            }.thenPost {
                val userListPostParam = UserListPostIdParam()
                params = userListPostParam

                onSuccess<List<User>> {
                    it.toLogD()
                }
            }.start()


        }

        httpLink2.setOnClickListener {
            val textView = Button(this)
            textView.setText("设置数据")
            textView.layoutParams = ViewGroup.LayoutParams(50.dp2px, 50.dp2px)
            val des = IntArray(2)
            httpLink2.getLocationInWindow(des)
            playAnimation(textView, bottom, des)
        }

    }

    private fun initData() {

    }

    fun playAnimation(moveView: View, endView: View, position: IntArray) {
        val rootView: ViewGroup = this.window.decorView as ViewGroup
        rootView.addView(moveView)
        val des = IntArray(2)
        endView.getLocationInWindow(des)

        /*动画开始位置，也就是物品的位置;动画结束的位置，也就是购物车的位置 */
        val startPosition = Point(position[0], position[1])
        val endPosition = Point(
            des[0] + endView.width / 2,
            des[1] + endView.height / 2
        )
        val pointX: Int = (startPosition.x + endPosition.x) / 2 - 100
        val pointY: Int = startPosition.y - 200
        val controllPoint = Point(pointX, pointY)

        /*
        * 属性动画，依靠TypeEvaluator来实现动画效果，其中位移，缩放，渐变，旋转都是可以直接使用
        * 这里是自定义了TypeEvaluator， 我们通过point记录运动的轨迹，然后，物品随着轨迹运动，
        * 一旦轨迹发生变化，就会调用addUpdateListener这个方法，我们不断的获取新的位置，是物品移动
        * */
        val valueAnimator =
            ValueAnimator.ofObject(BizierEvaluator2(controllPoint), startPosition, endPosition)
        valueAnimator.start()
        valueAnimator.addUpdateListener { valueAnimator ->
            val point: Point = valueAnimator.animatedValue as Point
            moveView.x = point.x.toFloat()
            moveView.y = point.y.toFloat()
        }
        /**
         * 动画结束，移除掉小圆圈
         */
        valueAnimator.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator?) {
                super.onAnimationEnd(animation)
                val rootView: ViewGroup =
                    this@HttpLinkActivity.getWindow().getDecorView() as ViewGroup
                rootView.removeView(moveView)
            }
        })
    }


    private fun globalScopeTest() {
//        runBlocking {
//
//        }

        GlobalScope.launch(Dispatchers.Main) {
            updateUI()
            val job = launch(Dispatchers.IO) {
                Thread.sleep(5000)
            }
            job.isCompleted.toLogD()
            job.invokeOnCompletion() {
                Thread.currentThread().name.toLogD()
                job.isCompleted.toLogD()
                httpLink1.text = "我已经完成了"
            }

            /**
             * 非阻塞式挂起
             */
            val job1 = launch {
                Thread.sleep(5000)
            }
            job1.invokeOnCompletion {
                Thread.currentThread().name.toLogD()
            }

            if (sleep5S()) {
                httpLink1.text = "我又睡了5秒"
            }

        }

//        val cor= CoroutineScope(this@HttpLinkActivity)
//        cor.launch {
//
//        }
    }

    private suspend fun sleep5S(): Boolean = withContext(Dispatchers.IO) {
        delay(5000)
        true
    }

    private fun updateUI() {
        "更改Button".toLogD()
        httpLink1.text = "更改Button"
    }
}


class BizierEvaluator2(private val controllPoint: Point) :
    TypeEvaluator<Point> {
    override fun evaluate(
        t: Float,
        startValue: Point,
        endValue: Point
    ): Point {
        val x =
            ((1 - t) * (1 - t) * startValue.x + 2 * t * (1 - t) * controllPoint.x + t * t * endValue.x).toInt()
        val y =
            ((1 - t) * (1 - t) * startValue.y + 2 * t * (1 - t) * controllPoint.y + t * t * endValue.y).toInt()
        return Point(x, y)
    }

}

class Point {
    var x = 0
    var y = 0

    constructor() {}
    constructor(x: Int, y: Int) {
        this.x = x
        this.y = y
    }

    constructor(src: Point) {
        x = src.x
        y = src.y
    }

    operator fun set(x: Int, y: Int) {
        this.x = x
        this.y = y
    }
}
