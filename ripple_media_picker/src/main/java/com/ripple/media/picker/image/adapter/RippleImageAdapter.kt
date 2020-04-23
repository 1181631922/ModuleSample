package com.ripple.media.picker.image.adapter

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ripple.media.picker.R
import com.ripple.media.picker.RippleMediaPick
import com.ripple.media.picker.model.RippleImageModel
import com.ripple.media.picker.model.RippleMediaModel
import com.ripple.media.picker.model.impl.RippleImageImpl
import com.ripple.media.picker.util.dp2px
import com.ripple.media.picker.util.screenwidth
import com.ripple.media.picker.view.RippleImageView

/**
 * Author: fanyafeng
 * Data: 2020/4/22 13:41
 * Email: fanyafeng@live.cn
 * Description:
 */
class RippleImageAdapter @JvmOverloads constructor(
    private val mContext: Context,
    private val list: List<RippleMediaModel>,
    private val line: Int = 4,
    private val showCamera: Boolean = false
) :
    RecyclerView.Adapter<RippleImageAdapter.RippleImageViewHolder>() {

    var SCREEN_WIDTH = (mContext as Activity).screenwidth()
    var ITEM_WIDTH = ((SCREEN_WIDTH - 4.dp2px) / line).toInt()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RippleImageViewHolder {
        val layout =
            LayoutInflater.from(mContext).inflate(R.layout.ripple_item_image_layout, parent, false)
        return RippleImageViewHolder(layout)
    }

    override fun getItemCount(): Int {

        return if (showCamera)
            list.size + 1
        else
            list.size
    }

    override fun onBindViewHolder(holder: RippleImageViewHolder, position: Int) {
        holder.imageItemCheck?.text = ""


        val model = list[position]

        val params = holder.imageItemLayout?.layoutParams
        params?.width = ITEM_WIDTH
        params?.height = ITEM_WIDTH

        val loadFrame = RippleMediaPick.getInstance().imageLoadFrame
        loadFrame?.displayImage(
            mContext,
            model.getPath(),
            holder.imageItemIcon!!,
            ITEM_WIDTH,
            ITEM_WIDTH
        )

        holder.imageItemCheck?.setOnClickListener {
            //点击选择图片
            RippleMediaPick.getInstance().imageList.add(model)
            holder.imageItemCheck?.text = "9"
        }
    }


    inner class RippleImageViewHolder(item: View) : RecyclerView.ViewHolder(item) {

        var imageItemLayout: RelativeLayout? = null
        var imageItemIcon: RippleImageView? = null
        var imageItemCheck: TextView? = null

        init {
            imageItemLayout = item.findViewById(R.id.imageItemLayout)
            imageItemIcon = item.findViewById(R.id.imageItemIcon)
            imageItemCheck = item.findViewById(R.id.imageItemCheck)
        }
    }
}