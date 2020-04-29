package com.ripple.media.picker.view.photoview.impl

import android.view.GestureDetector
import android.view.MotionEvent


/**
 * Author: fanyafeng
 * Data: 2020/4/29 17:24
 * Email: fanyafeng@live.cn
 * Description:
 */
class DefaultOnDoubleTapListener(private var photoViewAttacher: PhotoViewAttacher? = null) :
    GestureDetector.OnDoubleTapListener {

    init {
        setPhotoViewAttacher(photoViewAttacher)
    }

    fun setPhotoViewAttacher(newPhotoViewAttacher: PhotoViewAttacher?) {
        photoViewAttacher = newPhotoViewAttacher
    }

    override fun onSingleTapConfirmed(e: MotionEvent): Boolean {
        if (photoViewAttacher == null) return false
        val imageView = photoViewAttacher?.getImageView()
        if (null != photoViewAttacher?.getOnPhotoTapListener()) {
            val displayRect = photoViewAttacher!!.getDisplayRect()
            if (null != displayRect) {
                val x = e.x
                val y = e.y

                if (displayRect.contains(x, y)) {
                    val xResult = ((x - displayRect.left)
                            / displayRect.width())
                    val yResult = ((y - displayRect.top)
                            / displayRect.height())
                    photoViewAttacher!!.getOnPhotoTapListener()
                        .onPhotoTap(imageView, xResult, yResult)
                    return true
                }
            }
        }
        if (null != photoViewAttacher?.getOnViewTapListener()) {
            photoViewAttacher!!.getOnViewTapListener().onViewTap(imageView, e.x, e.y)
        }
        return false
    }

    override fun onDoubleTap(ev: MotionEvent): Boolean {
        if (photoViewAttacher == null) return false
        try {
            val scale = photoViewAttacher!!.getScale()
            val x = ev.x
            val y = ev.y
            if (scale < photoViewAttacher!!.getMediumScale()) {
                photoViewAttacher!!.setScale(photoViewAttacher!!.getMediumScale(), x, y, true)
            } else if (scale >= photoViewAttacher!!.getMediumScale() && scale < photoViewAttacher!!.getMaximumScale()) {
                photoViewAttacher?.getMaximumScale()
                    ?.let { photoViewAttacher!!.setScale(it, x, y, true) }
            } else {
                photoViewAttacher?.getMinimumScale()
                    ?.let { photoViewAttacher!!.setScale(it, x, y, true) }
            }
        } catch (e: ArrayIndexOutOfBoundsException) {
        }
        return true
    }

    override fun onDoubleTapEvent(e: MotionEvent?): Boolean {
        return false
    }

}