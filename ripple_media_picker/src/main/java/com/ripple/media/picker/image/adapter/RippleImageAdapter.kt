package com.ripple.media.picker.image.adapter

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.ripple.media.picker.R
import com.ripple.media.picker.RippleMediaPick
import com.ripple.media.picker.config.IImagePickConfig
import com.ripple.media.picker.model.RippleImageModel
import com.ripple.media.picker.model.RippleMediaModel
import com.ripple.media.picker.model.impl.RippleImageImpl
import com.ripple.media.picker.util.LogUtil
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
    private val config: IImagePickConfig = RippleMediaPick.getInstance().imagePickConfig,
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

        if (model.isCheck()) {
            model.getTag()?.let {
                holder.imageItemCheck?.text = (model.getTag() as Int).toString()
            }
        }

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
            val modelList = RippleMediaPick.getInstance().imageList

            LogUtil.d("选取图片数量图片数量：", msg = modelList.toString())

            /**
             * 如果选中的图片包括当前的图则取消选择
             * 否则的话添加选择
             *
             * 取消，选择后需要更新个数显示
             */
            if (modelList.contains(model)) {
                model.setCheck(false)
                model.setTag(null)
                modelList.remove(model)
                updateCount(list, modelList, holder.imageItemCheck)
                notifyDataSetChanged()
            } else {
                if (modelList.size < config.getCount()) {
                    model.setCheck(true)
                    modelList.add(model)
                    updateCount(list, modelList, holder.imageItemCheck)
                    notifyDataSetChanged()
                } else {
                    Toast.makeText(mContext, "图片最多选取" + config.getCount() + "张", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }

    /**
     * 更新选中右上角显示
     */
    private fun updateCount(
        allList: List<RippleMediaModel>,
        selectList: List<RippleMediaModel>,
        view: TextView?
    ) {
        allList.forEachIndexed { _, allItem ->
            selectList.forEachIndexed { selectIndex, selectItem ->
                if (allItem == selectItem) {
                    val seeIndex = selectIndex + 1
                    allItem.setTag(seeIndex)
                    view?.text = seeIndex.toString()
                }
            }
        }
    }

    inner class RippleImageViewHolder(item: View) : RecyclerView.ViewHolder(item) {

        var imageItemLayout: RelativeLayout? = null
        var imageItemIcon: RippleImageView? = null
        var imageItemCheck: TextView? = null
        var imageItemUnCheck: TextView? = null

        init {
            imageItemLayout = item.findViewById(R.id.imageItemLayout)
            imageItemIcon = item.findViewById(R.id.imageItemIcon)
            imageItemCheck = item.findViewById(R.id.imageItemCheck)
            imageItemUnCheck = item.findViewById(R.id.imageItemUnCheck)
        }
    }
}