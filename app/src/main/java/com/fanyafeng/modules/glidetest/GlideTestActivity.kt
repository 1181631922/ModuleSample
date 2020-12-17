package com.fanyafeng.modules.glidetest

import android.graphics.Bitmap
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.request.RequestOptions
import com.fanyafeng.modules.R
import com.fanyafeng.modules.ninegrid.GlideRoundTransformCenterCrop
import com.ripple.tool.density.dp2pxF
import kotlinx.android.synthetic.main.activity_glide_test.*
import java.security.MessageDigest

class GlideTestActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_glide_test)

        initView()
        initData()
    }

    private fun initView() {
        iv1()
//        Glide.with(this).load(R.drawable.category_skeleton_drawing_main).into(iv1)

    }

    private fun iv1() {
        Glide.with(this)
            .load(R.drawable.category_skeleton_drawing_main)
            .apply(RequestOptions.bitmapTransform(GlideLeftTopTransform()))
            .into(iv1)
    }

    private fun initData() {

    }
}

class GlideLeftTopTransform :
    BitmapTransformation() {

    companion object {
        val ID = "com.fanyafeng.glide.left.top.transform"
        val ID_BYTES = ID.toByteArray(CHARSET)
    }

    override fun updateDiskCacheKey(messageDigest: MessageDigest) {
        messageDigest.update(ID_BYTES)
    }

    override fun transform(
        pool: BitmapPool,
        toTransform: Bitmap,
        outWidth: Int,
        outHeight: Int
    ): Bitmap {
        val outRatio = outWidth.toFloat() / outHeight.toFloat()

        val inWidth = toTransform.width
        val inHeight = toTransform.height

        val inRatio = inWidth.toFloat() / inHeight.toFloat()

        return if (inRatio < outRatio) {
            val actWidth = inWidth.toFloat()
            val actHeight = actWidth / outRatio
            var targetBitmap = toTransform.copy(Bitmap.Config.ARGB_8888, true)
            targetBitmap =
                Bitmap.createBitmap(targetBitmap, 0, 0, actWidth.toInt(), actHeight.toInt())
            targetBitmap
        } else {
            toTransform
        }
    }

}