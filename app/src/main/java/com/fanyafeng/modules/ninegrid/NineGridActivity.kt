package com.fanyafeng.modules.ninegrid

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.fanyafeng.modules.BaseActivity
import com.fanyafeng.modules.R
import com.ripple.ui.RippleImageView
import com.ripple.ui.ninegridview.NineGridLoadFrame
import com.ripple.ui.ninegridview.NineGridViewAdapter
import com.ripple.ui.ninegridview.NineItem
import com.ripple.ui.ninegridview.SimpleNineItem
import com.ripple.ui.ninegridview.impl.NineGridAdapter
import com.ripple.ui.ninegridview.impl.NineGridImpl
import com.ripple.ui.ninegridview.impl.NineGridView
import kotlinx.android.synthetic.main.activity_nine_grid.*

class NineGridActivity : BaseActivity() {

    private var imageList: ArrayList<NineItem> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nine_grid)
        title = "图片九宫格"

        initView()
        initData()
    }

    private fun initView() {
        imageList.apply {
            add(
                SimpleNineItem("https://ss0.bdstatic.com/94oJfD_bAAcT8t7mm9GUKT-xh_/timg?image&quality=100&size=b4000_4000&sec=1589448934&di=7c200678673481d850c0370f1a2ae67e&src=http://b2-q.mafengwo.net/s5/M00/91/06/wKgB3FH_RVuATULaAAH7UzpKp6043.jpeg")
            )
//            add(
//                SimpleNineItem("https://ss0.bdstatic.com/94oJfD_bAAcT8t7mm9GUKT-xh_/timg?image&quality=100&size=b4000_4000&sec=1589448934&di=7c200678673481d850c0370f1a2ae67e&src=http://b2-q.mafengwo.net/s5/M00/91/06/wKgB3FH_RVuATULaAAH7UzpKp6043.jpeg")
//            )
//            add(
//                SimpleNineItem(
//                    "https://ss0.bdstatic.com/94oJfD_bAAcT8t7mm9GUKT-xh_/timg?image&quality=100&size=b4000_4000&sec=1589448934&di=7c200678673481d850c0370f1a2ae67e&src=http://b2-q.mafengwo.net/s5/M00/91/06/wKgB3FH_RVuATULaAAH7UzpKp6043.jpeg"
//                )
//            )
//            add(SimpleNineItem("https://ss0.bdstatic.com/94oJfD_bAAcT8t7mm9GUKT-xh_/timg?image&quality=100&size=b4000_4000&sec=1589448934&di=7c200678673481d850c0370f1a2ae67e&src=http://b2-q.mafengwo.net/s5/M00/91/06/wKgB3FH_RVuATULaAAH7UzpKp6043.jpeg"))
//            add(
//                SimpleNineItem(
//                    "https://ss0.bdstatic.com/94oJfD_bAAcT8t7mm9GUKT-xh_/timg?image&quality=100&size=b4000_4000&sec=1589448934&di=7c200678673481d850c0370f1a2ae67e&src=http://b2-q.mafengwo.net/s5/M00/91/06/wKgB3FH_RVuATULaAAH7UzpKp6043.jpeg"
//                )
//            )
        }

        gridView.loadFrame = MyLoadFrame()
        gridView.adapter = NineGridAdapter(this, imageList)


    }

    private fun initData() {

    }

}


class MyLoadFrame : NineGridLoadFrame {
    override fun displayImage(context: Context, path: String, imageView: RippleImageView) {
        Glide.with(context).load(path).into(imageView)
    }

}