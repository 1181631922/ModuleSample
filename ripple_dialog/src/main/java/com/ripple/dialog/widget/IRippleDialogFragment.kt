package com.ripple.dialog.widget

import android.content.Intent


/**
 * Author: fanyafeng
 * Data: 2020/6/23 16:05
 * Email: fanyafeng@live.cn
 * Description: 抽象FragmentDialog的统一行为
 */

interface IRippleDialogFragment : IRippleBaseDialog {

    /**
     * activity回调结果
     *
     * android原有的dialog并不支持
     * 只有在DialogFragment才有，因为DialogFragment是继承的Fragment
     */
    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?)

    /**
     * Return the identifier this fragment is known by.  This is either
     * the android:id value supplied in a layout or the container view ID
     * supplied when adding the fragment.
     */
    fun getId(): Int
}