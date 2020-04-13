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
import com.fanyafeng.modules.BaseActivity
import com.fanyafeng.modules.R
import com.fanyafeng.modules.common.MainModel
import com.fanyafeng.modules.permission.PermissionTestActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.item_function_list_layout.view.*

class MainActivity : BaseActivity() {

    private val funcList = mutableListOf<MainModel>()
    private var mainAdapter: MainAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        title = "主页"

        initView()
        initData()
    }

    private fun initView() {
        functionList.layoutManager = LinearLayoutManager(this)

    }

    private fun initData() {
        funcList.apply {
//            add(MainModel("主页", MainActivity::class.java))
            add(MainModel("动态权限", PermissionTestActivity::class.java))
        }

        mainAdapter = MainAdapter(this, funcList)
        functionList.adapter = mainAdapter
    }
}

class MainAdapter(private val mContext: Context, private val funcList: MutableList<MainModel>) :
    RecyclerView.Adapter<MainAdapter.MainViewHolder>() {

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

    inner class MainViewHolder(item: View) : RecyclerView.ViewHolder(item) {

        var functionListTitle: AppCompatButton? = null

        init {
            functionListTitle = item.findViewById(R.id.functionListTitle)
        }
    }
}
