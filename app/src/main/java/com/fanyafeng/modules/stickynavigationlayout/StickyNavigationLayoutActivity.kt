package com.fanyafeng.modules.stickynavigationlayout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.ViewPager
import com.fanyafeng.modules.R
import kotlinx.android.synthetic.main.activity_sticky_navigation_layout.*

class StickyNavigationLayoutActivity : AppCompatActivity(), ViewPager.OnPageChangeListener {

    private val tabList = mutableListOf("LinearLayout", "GridLayout")
    private val tabListFragment = mutableListOf<Pair<String, Fragment>>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sticky_navigation_layout)
        title = "嵌套滑动View"
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

        stickyNavigationLayoutViewPager.adapter = viewPagerAdapter
        stickyNavigationLayoutViewPager.offscreenPageLimit = 3
        stickyNavigationLayoutViewPager.addOnPageChangeListener(this)

        stickyNavigationLayoutIndicator.setupWithViewPager(stickyNavigationLayoutViewPager)
        stickyNavigationLayoutIndicator.setTabsFromPagerAdapter(viewPagerAdapter)
    }

    override fun onPageScrollStateChanged(state: Int) {
    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
    }

    override fun onPageSelected(position: Int) {
        title = tabList[position]
    }
}

class FragmentViewPagerAdapter(
    private val fm: FragmentManager,
    private val titleList: List<Pair<String, Fragment>>
) :
    FragmentStatePagerAdapter(fm, titleList.size) {
    override fun getItem(position: Int): Fragment {
        return titleList[position].second
    }

    override fun getCount(): Int {
        return titleList.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return titleList[position].first
    }

}
