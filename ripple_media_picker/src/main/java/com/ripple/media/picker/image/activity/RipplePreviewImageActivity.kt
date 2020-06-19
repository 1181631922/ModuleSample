package com.ripple.media.picker.image.activity

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.ripple.media.picker.R
import com.ripple.media.picker.RippleMediaPick
import com.ripple.media.picker.base.RippleBaseActivity
import com.ripple.media.picker.config.IPreviewImageConfig
import com.ripple.media.picker.config.impl.PreviewImageConfig
import com.ripple.media.picker.image.RippleImagePick
import com.ripple.media.picker.model.RippleMediaModel
import com.ripple.media.picker.util.screenHeight
import com.ripple.media.picker.util.screenwidth
import com.ripple.media.picker.view.photoview.PhotoView
import kotlinx.android.synthetic.main.activity_ripple_preview_image.*

class RipplePreviewImageActivity : RippleBaseActivity() {

    private var list: List<RippleMediaModel> = mutableListOf()
    private var photoViewAdapter: PhotoViewAdapter? = null
    private var currPosition: Int = 0

    private var config: IPreviewImageConfig = PreviewImageConfig.Builder().build()
    private var model = config.getModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ripple_preview_image)

        if (intent.getSerializableExtra(IPreviewImageConfig.PREVIEW_IMAGE_CONFIG) != null) {
            config =
                intent.getSerializableExtra(IPreviewImageConfig.PREVIEW_IMAGE_CONFIG) as IPreviewImageConfig
            list = config.getImageList()
            model = config.getModel()
            currPosition = config.getCurrentPosition()
        }

        initView()
        initData()
    }

    private fun initView() {
        imageItemCheck.background = resources.getDrawable(R.drawable.ripple_image_uncheck_shape)

    }

    override fun onResume() {
        super.onResume()
        when (model) {
            IPreviewImageConfig.PreviewModel.NORMAL -> {
                toolbarRightTitle?.visibility = View.GONE
                imageItemCheck.visibility = View.GONE
            }
            IPreviewImageConfig.PreviewModel.SELECT -> {
                toolbarRightTitle?.visibility = View.VISIBLE
                imageItemCheck.visibility = View.VISIBLE
            }
        }
        updateImageSizeView()
    }

    private fun updateImageSizeView() {
        val count = RippleMediaPick.getInstance().imageList.size
        if (count > 0) {
            toolbarRightTitle?.setTextColor(Color.WHITE)
//            toolbarRightTitle?.background =
//                resources.getDrawable(R.drawable.ripple_next_step_shape)
            toolbarRightTitle?.text = "确认($count)"
            toolbarRightTitle?.setOnClickListener {
                val imageList = RippleMediaPick.getInstance().imageList
                if (imageList.size > 0) {
                    val listener = RippleImagePick.getInstance().selectImageListListener
                    if (listener != null) {
                        listener.selectImageList(imageList)
                        RippleImagePick.getInstance().selectImageListListener = null
                    }

                    val intent = Intent()
                    intent.putExtra(
                        RippleImagePick.RESULT_IMG_LIST,
                        imageList
                    )
                    setResult(RESULT_OK, intent)
                    finish()
                }
            }
        } else {
            toolbarRightTitle?.setTextColor(Color.GRAY)
//            toolbarRightTitle?.background =
//                resources.getDrawable(R.drawable.ripple_next_step_unable_shape)
            toolbarRightTitle?.text = "确认"
            toolbarRightTitle?.setOnClickListener(null)
        }
        toolbarCenterTitle?.text = (currPosition + 1).toString() + "/" + list.size
        val model = list[currPosition]
        val selectImageList = RippleMediaPick.getInstance().imageList
        if (selectImageList.contains(model)) {
            imageItemCheck.text =
                (RippleMediaPick.getInstance().imageList.indexOf(model) + 1).toString()
            imageItemCheck.background =
                resources.getDrawable(R.drawable.ripple_image_check_shape)
        } else {
            imageItemCheck.text = ""
            imageItemCheck.background =
                resources.getDrawable(R.drawable.ripple_image_uncheck_shape)
        }
    }

    private fun initData() {
        imageItemCheck.setOnClickListener {
            val model = list[currPosition]
            val selectImageList = RippleMediaPick.getInstance().imageList
            if (selectImageList.contains(model)) {
                RippleMediaPick.getInstance().imageList.remove(model)
                imageItemCheck.text = ""
                imageItemCheck.background =
                    resources.getDrawable(R.drawable.ripple_image_uncheck_shape)
            } else {
                RippleMediaPick.getInstance().imageList.add(model)
                imageItemCheck.background =
                    resources.getDrawable(R.drawable.ripple_image_check_shape)
                imageItemCheck.text =
                    (RippleMediaPick.getInstance().imageList.indexOf(model) + 1).toString()
            }
            updateImageSizeView()
        }

        photoViewAdapter = PhotoViewAdapter()
        viewPager.adapter = photoViewAdapter
        viewPager.currentItem = currPosition
        viewPager.offscreenPageLimit = 3
        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
            }

            override fun onPageSelected(position: Int) {
                currPosition = position
                updateImageSizeView()
            }

        })
    }

    inner class PhotoViewAdapter : PagerAdapter() {

        override fun isViewFromObject(view: View, any: Any): Boolean {
            return view == any
        }

        override fun getCount() = list.size

        override fun instantiateItem(container: ViewGroup, position: Int): Any {
            val model = list[position]
            val rootView = LayoutInflater.from(this@RipplePreviewImageActivity)
                .inflate(R.layout.item_preview_image_layout, null)
            val photoPreview = rootView.findViewById<PhotoView>(R.id.photoPreview)
            RippleMediaPick.getInstance().imageLoadFrame?.displayImage(
                this@RipplePreviewImageActivity,
                model.getPath(),
                photoPreview,
                this@RipplePreviewImageActivity.screenwidth(),
                this@RipplePreviewImageActivity.screenHeight()
            )

            container.addView(rootView)
            return rootView
        }

        override fun destroyItem(container: ViewGroup, position: Int, any: Any) {
            container.removeView(any as View)
        }
    }
}
