package com.ripple.media.picker.image.activity

import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.GridLayoutManager
import com.ripple.media.picker.R
import com.ripple.media.picker.base.RippleBaseActivity
import com.ripple.media.picker.image.ScanImageSource
import com.ripple.media.picker.image.adapter.RippleImageAdapter
import com.ripple.media.picker.model.RippleFolderModel
import com.ripple.media.picker.model.RippleImageModel
import com.ripple.media.picker.model.RippleMediaModel
import com.ripple.media.picker.util.LogUtil
import kotlinx.android.synthetic.main.activity_ripple_image_picker.*

class RippleImagePickerActivity : RippleBaseActivity(), ScanImageSource.ImageSourceListener {

    companion object {
        private val TAG = RippleImagePickerActivity.javaClass.simpleName
        private val LINE = 4
    }

    private lateinit var scanImageSource: ScanImageSource
    private var adapter: RippleImageAdapter? = null
    private var list = ArrayList<RippleMediaModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ripple_image_picker)

        initView()
        initData()
    }

    private fun initView() {
        scanImageSource = ScanImageSource(this, null, this)
        rippleImageRV.layoutManager =
            GridLayoutManager(this, LINE, GridLayoutManager.VERTICAL, false)
    }

    private fun initData() {
        adapter = RippleImageAdapter(this, list, LINE)
    }

    /**
     * 图片加载完成
     */
    override fun onMediaLoaded(mediaList: List<RippleFolderModel>) {
        LogUtil.d(TAG, mediaList.toString())
        if (mediaList.isNotEmpty()) {
            list.addAll(mediaList[0].getMediaList())
            rippleImageRV.adapter = adapter
        }
    }
}
