package com.fanyafeng.modules.taobaomain

import android.app.Activity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.fanyafeng.modules.BaseActivity
import com.fanyafeng.modules.R
import com.fanyafeng.modules.stickynavigationlayout.FragmentViewPagerAdapter
import com.fanyafeng.modules.stickynavigationlayout.RVAdapter
import com.fanyafeng.modules.stickynavigationlayout.TabLayoutFragment
import com.ripple.tool.extend.forEach
import kotlinx.android.synthetic.main.activity_tao_bao_main.*
import java.util.ArrayList

class TaoBaoMainActivity : BaseActivity(), ViewPager.OnPageChangeListener {

    private val tabList = mutableListOf("LinearLayout", "GridLayout", "GridLayoutManager2")
    private val tabListFragment = mutableListOf<Pair<String, Fragment>>()

    private var layoutManager: RecyclerView.LayoutManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tao_bao_main)
        title = "防淘宝首页"
        initView()
        initData()
    }

    private fun initView() {
        tabList.forEachIndexed { index, it ->
            val fragment = TabLayoutFragment()
            val bundle = Bundle()
            bundle.putString("flag", index.toString())
            fragment.arguments = bundle
            tabListFragment.add(Pair(it, fragment))
        }
    }

    private fun initData() {
        val viewPagerAdapter = FragmentViewPagerAdapter(supportFragmentManager, tabListFragment)

        nestedViewPager.adapter = viewPagerAdapter
        nestedViewPager.offscreenPageLimit = 3
        nestedViewPager.addOnPageChangeListener(this)

        nestedIndicator.setupWithViewPager(nestedViewPager)
        nestedIndicator.setTabsFromPagerAdapter(viewPagerAdapter)

        layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        nestedTopRecyclerView.layoutManager = layoutManager
        val stringList = ArrayList<String>()
        10.forEach {
            stringList.add(TabLayoutFragment.IMG_URL)
        }
        val rvAdapter = RVAdapter(this, stringList)
        nestedTopRecyclerView.adapter = rvAdapter
    }

    override fun onPageScrollStateChanged(state: Int) {
    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
    }

    override fun onPageSelected(position: Int) {
        title = tabList[position]
    }
}