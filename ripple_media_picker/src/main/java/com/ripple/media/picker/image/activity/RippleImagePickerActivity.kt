package com.ripple.media.picker.image.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.ripple.media.picker.R
import com.ripple.media.picker.activity.RippleBaseActivity
import com.ripple.media.picker.image.ScanImageSource
import com.ripple.media.picker.model.RippleFolderModel
import com.ripple.media.picker.util.LogUtil

class RippleImagePickerActivity : RippleBaseActivity(), ScanImageSource.ImageSourceListener {

    companion object {
        private val TAG = RippleImagePickerActivity.javaClass.simpleName
    }

    private lateinit var scanImageSource: ScanImageSource

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ripple_image_picker)

        initView()
        initData()
    }

    private fun initView() {
        scanImageSource = ScanImageSource(this, null, this)

    }

    private fun initData() {

    }

    override fun onMediaLoaded(mediaList: List<RippleFolderModel>) {
        LogUtil.d(TAG, mediaList.toString())
    }
}
