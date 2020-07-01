package com.fanyafeng.modules.ninegrid

import android.content.Context
import android.os.Bundle
import android.view.View
import com.bumptech.glide.Glide
import com.bumptech.glide.load.MultiTransformation
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.fanyafeng.modules.BaseActivity
import com.fanyafeng.modules.R
import com.ripple.ui.ninegridview.NineGridLoadFrame
import com.ripple.ui.ninegridview.NineItem
import com.ripple.ui.ninegridview.NineItemListener
import com.ripple.ui.ninegridview.SimpleNineItem
import com.ripple.ui.ninegridview.impl.NineGridAdapter
import com.ripple.ui.ninegridview.impl.NineGridImpl
import com.ripple.ui.widget.RippleImageView
import com.ripple.tool.density.dp2pxF
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
            add(
                SimpleNineItem("https://ss0.bdstatic.com/94oJfD_bAAcT8t7mm9GUKT-xh_/timg?image&quality=100&size=b4000_4000&sec=1589448934&di=7c200678673481d850c0370f1a2ae67e&src=http://b2-q.mafengwo.net/s5/M00/91/06/wKgB3FH_RVuATULaAAH7UzpKp6043.jpeg")
            )

            add(
                SimpleNineItem(
                    "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1593613315329&di=9374e33743dcbdf91e5dd4b7d7dd8308&imgtype=0&src=http%3A%2F%2Fa3.att.hudong.com%2F13%2F41%2F01300000201800122190411861466.jpg"
                )
            )
            add(
                SimpleNineItem(
                    "https://ss0.bdstatic.com/94oJfD_bAAcT8t7mm9GUKT-xh_/timg?image&quality=100&size=b4000_4000&sec=1589448934&di=7c200678673481d850c0370f1a2ae67e&src=http://b2-q.mafengwo.net/s5/M00/91/06/wKgB3FH_RVuATULaAAH7UzpKp6043.jpeg"
                )
            )
            add(SimpleNineItem("https://ss0.bdstatic.com/94oJfD_bAAcT8t7mm9GUKT-xh_/timg?image&quality=100&size=b4000_4000&sec=1589448934&di=7c200678673481d850c0370f1a2ae67e&src=http://b2-q.mafengwo.net/s5/M00/91/06/wKgB3FH_RVuATULaAAH7UzpKp6043.jpeg"))
            add(
                SimpleNineItem(
                    "https://ss0.bdstatic.com/94oJfD_bAAcT8t7mm9GUKT-xh_/timg?image&quality=100&size=b4000_4000&sec=1589448934&di=7c200678673481d850c0370f1a2ae67e&src=http://b2-q.mafengwo.net/s5/M00/91/06/wKgB3FH_RVuATULaAAH7UzpKp6043.jpeg"
                )
            )
        }

        gridView.loadFrame = MyLoadFrame()
        gridView.nineGridConfig = NineGridImpl(maxLine = 1)
        val maxCount =
            gridView.nineGridConfig.getMaxLine() * gridView.nineGridConfig.getPerLineCount()
        gridView.adapter = NineGridAdapter(this, imageList, maxCount)
        gridView.nineItemListener = object : NineItemListener.SimpleNineItemListener {
            override fun onClickListener(view: View, item: NineItem, position: Int) {
                println("我被点击了：" + position)
            }
        }

        gridView.onItemClickListener={first, second, third ->
            println("我被点击了：" + third)
        }


    }

    private fun initData() {

    }

}


class MyLoadFrame : NineGridLoadFrame {
    override fun displayImage(context: Context, path: String, imageView: RippleImageView) {
        Glide.with(context)
            .load(path)
            .apply(RequestOptions.bitmapTransform(GlideRoundTransformCenterCrop(4.dp2pxF)))
            .into(imageView)
    }
}