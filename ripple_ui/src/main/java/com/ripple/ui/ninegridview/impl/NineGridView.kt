package com.ripple.ui.ninegridview.impl

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import com.ripple.ui.RippleImageView
import com.ripple.ui.ninegridview.NineGrid
import com.ripple.ui.ninegridview.NineGridLoadFrame
import com.ripple.ui.ninegridview.NineItem
import com.ripple.ui.ninegridview.NineItemListener
import java.io.Serializable

/**
 * Author: fanyafeng
 * Data: 2020/5/13 14:02
 * Email: fanyafeng@live.cn
 * Description: 图片九宫格view
 * 主要是以下两种方式：
 * 单张图片：
 *
 *           屏幕宽度
 * ---------------------------
 * 图片
 * -----------
 * |         |
 * |         |
 * |         |
 * |         |
 * |         |
 * |         |
 * -----------
 *
 * ----------------------------
 *
 * 再有就是多张图
 * 多张图可以设置一行最多显示多少图片 eg:3
 * 再有就是最多显示多少行 eg:3
 * 图片宽高比是一比一
 * eg:如果现在是四张图
 * 那么第一行显示三张，平分view宽度，
 * 三张的话还剩下一张去显示，那样的话就需要和第一行的排布一样
 * 比例还是一比一，但是后面两个空位也会占用三分之二
 * 类似权重，虽然不显示但是需要占位
 * 可以直接类比微信
 *
 *                       屏幕宽度
 * ----------------------------------------
 * ----------    ----------     -----------
 * |        |    |        |     |         |
 * |        |    |        |     |         |
 * |        |    |        |     |         |
 * |        |    |        |     |         |
 * ----------    ----------     -----------
 * |        |    |        |     |         |
 * |        |    |        |     |         |
 * |        |    |        |     |         |
 * |        |    |        |     |         |
 * ----------    ----------     -----------
 * |        |    |        |     |         |
 * |        |    |        |     |         |
 * |        |    |        |     |         |
 * |        |    |        |     |         |
 * ----------    ----------     -----------
 * ----------------------------------------
 */
class NineGridView @JvmOverloads constructor(
    private val mContext: Context?,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) :
    ViewGroup(mContext, attrs, defStyleAttr), NineGrid {

    companion object {
        private val TAG = NineGridView::class.java.simpleName

        /**
         * 一行最多显示多少张图片
         */
        private const val PER_LINE_COUNT = 3

        /**
         * 最多显示多少行
         */
        private const val MAX_LINE = 3

        /**
         * 图片最大显示数量
         */
        private const val MAX_SIZE = 9
    }

    private var currentLine = 0

    private val viewList: ArrayList<RippleImageView> = arrayListOf()

    var nineItemListener: NineItemListener? = null

    var loadFrame: NineGridLoadFrame? = null

    /**
     * 回调监听
     */
    var onItemClickListener: NineItemListener? = null

    /**
     * 属性配置
     */
    var nineGridConfig: NineGrid = NineGridImpl()

    /**
     * 可以自定义配置
     */

    init {

    }

    /**
     * 每一横行的view
     */
    private val itemHorizontalLayout = LinearLayout(mContext)

    /**
     * 包裹横行的纵行view
     */
    private val verticalLayout = LinearLayout(mContext)

    override fun setDivide(divide: Int) {
        nineGridConfig.setDivide(divide)
    }

    override fun getDivide(): Int {
        return nineGridConfig.getDivide()
    }

    override fun setSingleWidth(singleWidth: Int) {
        nineGridConfig.setSingleWidth(singleWidth)
    }

    override fun getSingleWidth(): Int {
        return nineGridConfig.getSingleWidth()
    }

    override fun setSingleImageRatio(ratio: Float) {
        nineGridConfig.setSingleImageRatio(ratio)
    }

    override fun getSingleImageRatio(): Float {
        return nineGridConfig.getSingleImageRatio()
    }

    private var mImageList: List<NineItem>? = null

    var adapter: NineGridViewAdapter? = null
        set(value) {
            field = value
            var size = value?.getImageList()?.size ?: 0
            currentLine = if (size % PER_LINE_COUNT == 0) {
                size / PER_LINE_COUNT
            } else {
                size / PER_LINE_COUNT + 1
            }

            val imageList = value?.getImageList()
            if (imageList?.isNotEmpty() == true) {
                visibility = View.VISIBLE
                val trueList = if (size > MAX_SIZE) imageList.subList(0, MAX_SIZE) else imageList
                size = trueList.size

                if (mImageList != null) {
                    val oldViewCount = mImageList!!.size
                    val newViewCount = size

                    if (oldViewCount > newViewCount) {
                        removeViews(newViewCount, oldViewCount - newViewCount)
                    } else if (oldViewCount < newViewCount) {
                        for (i in oldViewCount until newViewCount) {
                            val item = getItemView(i)
                            item?.let {
                                addView(it, generateDefaultLayoutParams())
                            }
                        }
                    }

                } else {
                    (0 until size).forEachIndexed { _, i ->
                        Log.d(TAG, "我看看我的i:" + i)
                        val item = getItemView(i)
                        item?.let {
                            addView(it, generateDefaultLayoutParams())
                        }
                    }
                }


            } else {
                visibility = View.GONE
            }

            //最后一个条目显示

            mImageList = imageList
            requestLayout()
        }

    init {
        /**
         * 需要添加view
         */
    }

    private var mWidth: Int? = null
    private var mHeight: Int? = null


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        var width = MeasureSpec.getSize(widthMeasureSpec)
        var height = 0
        var totalWidth = width - paddingLeft - paddingRight
        val imageList = adapter?.getImageList()
        val singleWidth = getSingleWidth()
        val ratio = getSingleImageRatio()
        val divide = getDivide()
        if (imageList?.isNotEmpty() == true) {
            if (imageList.size == 1) {
                mWidth = if (singleWidth > totalWidth) totalWidth else singleWidth
                mWidth?.let { nineGridWidth ->
                    mHeight = (nineGridWidth / ratio).toInt()
                    mHeight?.let { nineGridHeight ->
                        if (nineGridHeight > singleWidth) {
                            val ratio = singleWidth * 1F / nineGridHeight
                            mWidth = (nineGridWidth * ratio).toInt()
                            mHeight = singleWidth
                        }
                    }
                }
            } else {
                mWidth = (totalWidth - divide * (PER_LINE_COUNT - 1)) / PER_LINE_COUNT
                mHeight = mWidth
            }
            mWidth?.let {
                width =
                    it * PER_LINE_COUNT + divide * (PER_LINE_COUNT - 1) + paddingLeft + paddingRight
            }
            mHeight?.let {
                height = it * currentLine + divide * (currentLine - 1) + paddingTop + paddingBottom
            }
        }
        setMeasuredDimension(width, height)
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        Log.d(TAG, "onLayout 获取宽度：" + width)
        Log.d(TAG, "onLayout 获取高度：" + height)

        /**
         * 需要在此处添加view
         * 根据图片列表的数量进行不同形式的布局
         */

        val divide = getDivide()

        val imageList = adapter?.getImageList()
        imageList?.forEachIndexed { index, item ->
            val itemView = getChildAt(index) as RippleImageView

            val rowNumber: Int = index / PER_LINE_COUNT
            val columnNumber: Int = index % PER_LINE_COUNT
            val left = (mWidth ?: 0 + divide) * columnNumber + paddingLeft
            val top = (mHeight ?: 0 + divide) * rowNumber + paddingTop
            val right = left + (mWidth ?: 0)
            val bottom = top + (mHeight ?: 0)

            itemView.layout(left, top, right, bottom)

            loadFrame?.displayImage(context, item.getPath(), itemView)

        }
    }

    private fun getItemView(position: Int): RippleImageView? {
        Log.d(TAG, "我看看我的position:" + position)
        adapter?.let {
            val itemView: RippleImageView
            val listSize = viewList.size
            val model = it.getImageList()[position]
            if (position < listSize) {
                itemView = viewList[position]
            } else {
                itemView = adapter?.onCreateView(context) ?: RippleImageView(context)
                itemView.setOnClickListener { view ->
                    nineItemListener?.onClickListener(
                        view,
                        model,
                        position
                    )
                }

                itemView.setOnLongClickListener { view: View ->
                    nineItemListener?.onLongClickListener(view, model, position) ?: false
                }

                viewList.add(itemView)
            }
            return itemView
        }
        return null
    }


    abstract class NineGridViewAdapter(
        private val mContext: Context,
        private var list: List<NineItem>
    ) : Serializable {

        fun onCreateView(context: Context): RippleImageView {
            val itemView = RippleImageView(context)
            itemView.scaleType = ImageView.ScaleType.CENTER_CROP
            return itemView
        }

        fun setImageList(list: List<NineItem>) {
            this.list = list
        }

        fun getImageList(): List<NineItem> = list

    }
}
