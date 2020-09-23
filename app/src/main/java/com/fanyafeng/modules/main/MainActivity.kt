package com.fanyafeng.modules.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatButton
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.*
import com.fanyafeng.modules.BaseActivity
import com.fanyafeng.modules.BuildConfig
import com.fanyafeng.modules.R
import com.fanyafeng.modules.cache.CacheTestActivity
import com.fanyafeng.modules.common.MainModel
import com.fanyafeng.modules.dialog.RippleDialogActivity
import com.fanyafeng.modules.flowlayout.FlowLayoutActivity
import com.fanyafeng.modules.http.HttpActivity
import com.fanyafeng.modules.http.HttpLinkActivity
import com.fanyafeng.modules.permission.PermissionTestActivity
import com.fanyafeng.modules.mediapick.MediaPickActivity
import com.fanyafeng.modules.ninegrid.NineGridActivity
import com.fanyafeng.modules.stickynavigationlayout.StickyNavigationLayoutActivity
import com.fanyafeng.modules.task.HandleTaskActivity
import com.fanyafeng.modules.testfoldview.FoldViewActivity
import com.fanyafeng.modules.testlog.TestLogActivity
import com.ripple.log.extend.logD
import com.ripple.log.tpyeextend.toLogD
import com.ripple.sdk.router.hepler.PageRouterName
import com.ripple.tool.date.long2Date
import com.ripple.tool.extend.forEach
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.item_function_list_layout.view.*

@PageRouterName("myname", pageName = "测试")
class MainActivity : BaseActivity() {

    private val funcList = mutableListOf<MainModel>()
    private var mainAdapter: MainAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        logD("APP开始启动时间 Activity onCreate：" + System.currentTimeMillis().long2Date())
        setContentView(R.layout.activity_main)
        title = "主页"

        supportActionBar?.subtitle = "副主题"

        initView()
        initData()
    }

    private fun initView() {
        functionList.layoutManager = LinearLayoutManager(this)

        functionList.addOnScrollListener(object : OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                when (newState) {
                    /**
                     * 暂定状态
                     */
                    SCROLL_STATE_IDLE -> {
                        println("暂停状态")
                        //静默加载应放在此处
//                        val layoutManager = recyclerView.layoutManager as LinearLayoutManager
//                        val lastVisiblePosition = layoutManager.findLastVisibleItemPosition()
//                        println("newState最后一个可见view：" + lastVisiblePosition)
//                        if (funcList.size - lastVisiblePosition <= 20) {
//                            40.forEach {
//                                funcList.add(MainModel("媒体库选择", MediaPickActivity::class.java))
//                            }
//                            mainAdapter?.notifyDataSetChanged()
//                        }
                    }
                    /**
                     * 拖动状态
                     */
                    SCROLL_STATE_DRAGGING -> {
                        println("拖动状态")

                    }
                    /**
                     *
                     */
                    SCROLL_STATE_SETTLING -> {
                        println("这是啥")
                    }
                }


            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
//                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
//                val lastVisiblePosition = layoutManager.findLastVisibleItemPosition()
//                println("newState最后一个可见view：" + lastVisiblePosition)
//                if (funcList.size - lastVisiblePosition <= 20) {
//                    40.forEach {
//                        funcList.add(MainModel("媒体库选择", MediaPickActivity::class.java))
//                    }
//                    mainAdapter?.notifyDataSetChanged()
//                }
            }

        })


    }

    private fun initData() {
        funcList.apply {
            add(MainModel("媒体库选择", MediaPickActivity::class.java))
            add(MainModel("动态权限", PermissionTestActivity::class.java))
            add(MainModel("图片九宫格", NineGridActivity::class.java))
            add(MainModel("网络请求", HttpActivity::class.java))
            add(MainModel("多任务处理", HandleTaskActivity::class.java))
            add(MainModel("dialog封装", RippleDialogActivity::class.java))
            add(MainModel("流式layout", FlowLayoutActivity::class.java))
            add(MainModel("可折叠layout", FoldViewActivity::class.java))
            add(MainModel("嵌套滑动", StickyNavigationLayoutActivity::class.java))
            add(MainModel("测试log工具类", TestLogActivity::class.java))
            add(MainModel("测试http链式调用", HttpLinkActivity::class.java))
            add(MainModel("测试缓存框架", CacheTestActivity::class.java))
        }

//        20.forEach {
//            funcList.add(MainModel("媒体库选择", MediaPickActivity::class.java))
//        }

        mainAdapter = MainAdapter(this, funcList)
        functionList.adapter = mainAdapter
    }
}

class MainAdapter(private val mContext: Context, private val funcList: MutableList<MainModel>) :
    Adapter<MainAdapter.MainViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val item =
            LayoutInflater.from(mContext).inflate(R.layout.item_function_list_layout, parent, false)
        return MainViewHolder(item)
    }

    override fun getItemCount(): Int {
        return funcList.size
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        val item = funcList[position]
        holder.functionListTitle?.text = item.title
        holder.itemView.functionListTitle.setOnClickListener {
            val intent = Intent(mContext, item.clazz)
            mContext.startActivity(intent)
        }
    }

    inner class MainViewHolder(item: View) : ViewHolder(item) {

        var functionListTitle: AppCompatButton? = null

        init {
            functionListTitle = item.findViewById(R.id.functionListTitle)
        }
    }
}
